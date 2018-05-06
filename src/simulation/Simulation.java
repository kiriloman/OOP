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
    private static Point initialPoint, finalPoint;
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

    //dist function
    private static int dist(Individual z) {
        return Math.abs(z.getPosition().getX() - finalPoint.getX()) + Math.abs(z.getPosition().getY() - finalPoint.getY());
    }

    //length(z)-number of edges transversed by the individual z
    private static int length(Individual z) {
        if (z.getPath() == null) {
            return 0;
        } else {
            return z.getPath().size();
        }
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


            comfort = ((double) Math.pow(1 - (hst.getCost() - length(hst) + 2) / ((cmax - 1) * length(hst) + 3), comfortSens)) * Math.pow(1 - ((double) dist(hst) / ((double) colms + rows + 1)), comfortSens);
            hst.setComfort(comfort);


            Move m = new Move(-(1 - Math.log(comfort) * delta) * Math.log(1 - random.nextDouble()));
            Reproduction r = new Reproduction(-(1 - Math.log(comfort) * rho) * Math.log(1 - random.nextDouble()));
            Death d = new Death(-(1 - Math.log(1 - comfort) * mu) * Math.log(1 - random.nextDouble()));

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
            System.out.println(Population.size);
            System.out.println("Epidemic");
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
        double timeInterval = (double) finalInst / d;

        ////////IMPRIMIR///////////////////////////////////////////////////////////////CATA//////////////
        for (int t = time + 1; t <= finalInst; t++) {
            /*intervalo para imprimir*/
            if (t == auxTime + timeInterval) {
                EventPrint ep = new EventPrint(t);
                addEventToPec(ep);
                auxTime = t;
            }
        }

        while (pec.eventQueue.size() != 0 && Population.size != 0) {
            epidemic();
            Event event = pec.getEvent();
            Individual hst;


            if (event instanceof Move) {
                hst = ((Move) event).getHost();
                comfort = ((double) Math.pow(1 - (hst.getCost() - length(hst) + 2) / ((cmax - 1) * length(hst) + 3), comfortSens)) * Math.pow(1 - ((double) dist(hst) / ((double) colms + rows + 1)), comfortSens);
                event.execute();


                //Verifica se a nova posiçao e o ponto final
                //faz update da bestpath se o individuo chegou ao ponto final e tem uma path melhor
                if (hst.getPosition().equals(finalPoint)) {
                    if (!finalPointHit)
                        finalPointHit = true;
                    if (hst.getCost() < bestPathCost) {
                        bestPathCost = hst.getCost();
                        bestPathComfort = hst.getComfort();
                        bestPath = new ArrayList<>(hst.getPath());
                    }
                }

                Move mvs = new Move(event.getTime() + (-(1 - Math.log(comfort) * delta) * Math.log(1 - random.nextDouble())));
                //System.out.println("MOVE TIME: " + mvs.getTime());
                mvs.setHost(((Move) event).getHost());
                //System.out.println(((Move) event).getHost() + " host " + ((Move) event).getHost().getPosition() + " position");
                addEventToPec(mvs);
                numOfEvents++; ////////////////////////////////CATA//////////////
            }

            if (event instanceof Reproduction) {
                event.execute();
                //por no evento reproduction dalguma forma
                Individual parent = ((Reproduction) event).getHost();

                Individual child = new Individual(Population.nextChildId);
                //path do child
                double parameter1 = (double) (((double) 0.9) * ((double) parent.getPath().size()));
                double parameter2 = (double) (((double) 0.1) * ((double) parent.getComfort()));
                int sizeChildPath = (int) Math.ceil(parameter1 + parameter2);
                //rever com sublists
                for (int j = 0; j < sizeChildPath; j++) {
                    child.addToPath(parent.getPath().get(j));
                }
                //posiçao do child
                child.setPosition(child.getPath().get(sizeChildPath - 1));
                //comfort do child
                double childComfort = ((double) Math.pow(1 - (child.getCost() - length(child) + 2) / ((cmax - 1) * length(child) + 3), comfortSens)) * Math.pow(1 - ((double) dist(child) / ((double) colms + rows + 1)), comfortSens);
                child.setComfort(childComfort);
                //costPath do child
                for (int j = 0; j < sizeChildPath; j++) {
                    child.addToCostPath(parent.getCostPath().get(j));
                }
                //cost do child
                child.setCost(child.getCostPath().get(sizeChildPath - 1));

                Population.addIndividual(child);
                Move cMove = new Move(event.getTime() + (-(1 - Math.log(comfort) * delta) * Math.log(1 - random.nextDouble())));
                Reproduction cReproduction = new Reproduction(event.getTime() + (-(1 - Math.log(comfort) * rho) * Math.log(1 - random.nextDouble())));
                Death cDeath = new Death(event.getTime() + (-(1 - Math.log(1 - comfort) * mu) * Math.log(1 - random.nextDouble())));
                cMove.setHost(child);
                cReproduction.setHost(child);
                cDeath.setHost(child);
                addEventToPec(cDeath);
                addEventToPec(cMove);
                addEventToPec(cReproduction);
                numOfEvents++; ////////////////////////////////CATA//////////////
            }

            if (event instanceof Death) {
                event.execute();
                numOfEvents++; ////////////////////////////////CATA//////////////
            }

            if (event instanceof EventPrint) {
                observationNum = observationNum + 1;//supos que numero de observações seria o numero de prints feitos até ao momento.
                instant = event.getTime();
                if (!finalPointHit) {
                    Individual bestIndividual = Population.getBestFitIndividual();
                    if (bestIndividual.getComfort() > bestPathComfort) {
                        bestPath = new ArrayList<>(bestIndividual.getPath());
                        bestPathComfort = bestIndividual.getComfort();
                        bestPathCost = bestIndividual.getCost();
                    }
                }
                event.execute();
                if (!finalPointHit) {
                    bestPath = new ArrayList<>();
                    bestPathCost = Integer.MAX_VALUE;
                    bestPathComfort = -1;
                }
            }
        }
        System.out.println("FIM");
    }

    //decides and adds event to pec
    private static void addEventToPec(Event event) {
        if (event.getTime() <= finalInst)
            pec.addEvent(event);
    }

    //update bestPath
    private static void updateBestVars() {
        for (Individual individual : population.individuals) {
            if (individual.getCost() < bestPathCost) {
                bestPathCost = individual.getCost();
                bestPathComfort = individual.getComfort();
                bestPath = individual.getPath();
            }
        }
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