package simulation;

import grid.Point;
import grid.Map;
import pec.*;
import population.Individual;
import population.Population;
import main.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//Classe Simulation. Pensei em por o main aqui. mas pode-se mudar.
//Vai simular obviamente.
public class Simulation {
    public static int mu, delta, rho, colms, rows, initialPop, maxPop, finalInst, comfortSens,
            observationNum, numOfEvents, cmax; //rever isto
    private static double comfort;
    public static boolean finalPointHit;
    public static List<Point> bestPath;
    public static double bestPathCost, bestPathComfort, instant;
    public static Point initialPoint, finalPoint;
    private static HashMap<List<Point>, Integer> specialCostZones;
    private static List<Point> obstacles;
    private static Parser parser;
    private static Random random;
    private static Population population;
    private static Map map;
    private static PEC pec;


    public Simulation() {
        bestPathCost = Integer.MAX_VALUE;
        bestPathComfort = -1;
        bestPath = new ArrayList<>();
    }


    // retira toda a informaçao necessario do ficheiro XML
    public static void parseFile(String filePath) throws ParserConfigurationException, SAXException, IOException {
        parser = new Parser(filePath);
        mu = parser.readMu();
        delta = parser.readDelta();
        rho = parser.readRho();
        colms = parser.readNumberOfColumns();
        rows = parser.readNumberOfRows();
        initialPop = parser.readInitialPopulation();
        maxPop = parser.readMaxPopulation();
        finalInst = parser.readFinalInstant();
        comfortSens = parser.readComfortSens();
        initialPoint = parser.readInitialPoint();
        finalPoint = parser.readFinalPoint();
        specialCostZones = (HashMap<List<Point>, Integer>) parser.readSpecialCosts();
        obstacles = parser.readObstacles();
    }

    public static void createMap() {
        map = new Map(colms, rows, obstacles, specialCostZones);
        map.createGrid();
    }


    public static void createEventObservations() {
        int time = 0, auxTime = 0;
        double d = 20;
        double timeInterval = (double) finalInst / d;
        for (int t = time + 1; t <= finalInst; t++) {
            /*intervalo para imprimir*/
            if (t == auxTime + timeInterval) {
                EventPrint ep = new EventPrint(t);
                addEventToPec(ep);
                auxTime = t;
            }
        }
    }


    public static void initializePopulation() {

        population = new Population(initialPop);
        cmax = map.MaxCost();
        pec = new PEC();

        for (int ind = 0; ind < population.size; ind++) {
            Individual hst = population.individuals.get(ind);
            hst.setPosition(initialPoint);
            //mudar nome e talvez implementaçao no individual
            hst.addToCostPath(0);
            hst.addToPath(initialPoint);


            comfort = QuickMaths.calculateComfort(hst);
            hst.setComfort(comfort);


            Move m = new Move(QuickMaths.moveParameter(comfort, delta, random));
            Reproduction r = new Reproduction(QuickMaths.reproductionParameter(comfort, rho, random));
            Death d = new Death(QuickMaths.deathParameter(comfort, mu, random));

            d.setHost(hst);
            m.setHost(hst);
            r.setHost(hst);

            addEventToPec(d);
            addEventToPec(m);
            addEventToPec(r);


        }
    }

    public static void epidemic() {
        if (Population.size >= maxPop) {
            List<List<Object>> individualsComfort = new ArrayList<>();
            for (Individual i : Population.individuals) {
                List<Object> indComf = new ArrayList<>();
                indComf.add(i);
                indComf.add(i.getComfort());
                individualsComfort.add(indComf);
            }

            sort(individualsComfort);

            //creates the list of individuals that survive for sure
            List<Integer> survivors = new ArrayList<>();
            for (int s = 0; s < 5; s++) {
                survivors.add(((Individual) individualsComfort.get(s).get(0)).getId());
            }

            //epidemic strikes!
            for (int i = 0; i < Population.size; i++) {
                if (!survivors.contains(Population.individuals.get(i).getId()) && (Population.individuals.get(i).getComfort() <= Math.random())) {
                    population.killIndividual(pec, Population.individuals.get(i));
                }
            }

        }
    }

    public static void updateBestPath(Individual hst) {
        if (hst.getPosition().equals(finalPoint)) {
            if (!finalPointHit) {
                finalPointHit = true;
                bestPathCost = Integer.MAX_VALUE;
                bestPathComfort = -1;
                bestPath = new ArrayList<>();
            }
            if (hst.getCost() < bestPathCost) {
                bestPathCost = hst.getCost();
                bestPathComfort = hst.getComfort();
                bestPath = new ArrayList<>(hst.getPath());
            }
        } else {
            if (!finalPointHit) {
                if (hst.getComfort() > bestPathComfort) {
                    bestPathCost = hst.getCost();
                    bestPathComfort = hst.getComfort();
                    bestPath = new ArrayList<>(hst.getPath());
                }
            }
        }
    }

    // simula
    public static void simulate() throws IOException, SAXException, ParserConfigurationException {
        int time = 0;
        random = new Random();
        numOfEvents = 0; //////////////////////////CATA//////////////////////
        double d = 20;
        double auxTime = 0;//////////////////////////CATA//////////////////////
        observationNum = 0;//////////////////////////CATA//////////////////////

        parseFile("src/data1.xml");
        createMap();
        initializePopulation();
        createEventObservations();


        while (pec.eventQueue.size() != 0) {
            epidemic();
            Event event = pec.getEvent();
            Individual hst;

            if (event instanceof Move) {
                hst = ((Move) event).getHost();
                comfort = QuickMaths.calculateComfort(hst);
                event.execute();


                //Verifica se a nova posiçao e o ponto final
                //faz update da bestpath se o individuo chegou ao ponto final e tem uma path melhor
                updateBestPath(hst);

                Move mvs = new Move(event.getTime() + QuickMaths.moveParameter(comfort, delta, random));
                mvs.setHost(((Move) event).getHost());
                addEventToPec(mvs);
                numOfEvents++;
            }

            if (event instanceof Reproduction) {
                event.execute();

                Individual child = Population.individuals.get(Population.size - 1);
                double childComfort = child.getComfort();
                Move cMove = new Move(event.getTime() + QuickMaths.moveParameter(childComfort, delta, random));
                Reproduction cReproduction = new Reproduction(event.getTime() + QuickMaths.reproductionParameter(childComfort, rho, random));
                Death cDeath = new Death(event.getTime() + QuickMaths.deathParameter(childComfort, mu, random));
                cMove.setHost(child);
                cReproduction.setHost(child);
                cDeath.setHost(child);
                addEventToPec(cDeath);
                addEventToPec(cMove);
                addEventToPec(cReproduction);
                numOfEvents++;
            }

            if (event instanceof Death) {
                event.execute();
                numOfEvents++;
            }


            if ((event instanceof EventPrint)) {
                observationNum = observationNum + 1;
                instant = event.getTime();
                event.execute();
            }
        }
    }


    //decides and adds event to pec
    private static void addEventToPec(Event event) {
        if (event.getTime() <= finalInst)
            pec.addEvent(event);
    }

    //helps to sort the individuals by comfort in order to choose the first 5 when/if the epidemic strikes.
    private static void sort(List<List<Object>> listOfLists) {
        Collections.sort(listOfLists, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> o1, List<Object> o2) {
                Object o1Comf = o1.get(1);
                Object o2Comf = o2.get(1);
                return ((Double) o1Comf).compareTo((Double) o2Comf);
            }
        });
        Collections.reverse(listOfLists);
    }
}