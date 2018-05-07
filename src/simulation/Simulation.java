package simulation;

import grid.Map;
import grid.Point;
import main.Parser;
import org.xml.sax.SAXException;
import pec.*;
import population.Individual;
import population.Population;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

//Classe Simulation. Pensei em por o main aqui. mas pode-se mudar.
//Vai simular obviamente.
public class Simulation {
    public static int mu, delta, rho, colms, rows, initialPop, maxPop, finalInst, comfortSens,
            cmax; //rever isto
    private static double comfort;
    public static List<Point> bestPath;
    public static double bestPathCost, bestPathComfort;
    public static Point initialPoint, finalPoint;
    private static HashMap<List<Point>, Integer> specialCostZones;
    private static List<Point> obstacles;
    private static Parser parser;
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
        specialCostZones = parser.readSpecialCosts();
        obstacles = parser.readObstacles();
    }

    public static void createMap() {
        map = new Map(colms, rows, obstacles, specialCostZones, finalPoint);
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
                ep.addToPec();
                auxTime = t;
            }
        }
    }


    public static void initializePopulation() {

        population = new Population(initialPop);
        cmax = map.MaxCost();
        pec = new PEC(finalInst);

        for (int ind = 0; ind < population.size; ind++) {
            Individual individual = population.individuals.get(ind);
            individual.setPosition(initialPoint);
            //mudar nome e talvez implementaçao no individual
            individual.addToCostPath(0);
            individual.addToPath(initialPoint);

            comfort = QuickMaths.calculateComfort(individual);
            individual.setComfort(comfort);

            Move m = new Move(QuickMaths.moveParameter(comfort));
            Reproduction r = new Reproduction(QuickMaths.reproductionParameter(comfort));
            Death d = new Death(QuickMaths.deathParameter(comfort));

            d.setHost(individual);
            m.setHost(individual);
            r.setHost(individual);

            d.addToPec();
            m.addToPec();
            r.addToPec();
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
                    Population.killIndividual(Population.individuals.get(i));
                    i--;
                }
            }
        }
    }

    // simula
    public static void simulate() throws IOException, SAXException, ParserConfigurationException {
        parseFile("src/data1.xml");
        createMap();
        initializePopulation();
        createEventObservations();

        while (PEC.eventQueue.size() != 0) {
            epidemic();
            pec.executeEvent();
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