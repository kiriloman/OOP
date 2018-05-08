package simulation;

import grid.Map;
import grid.Point;
import org.xml.sax.SAXException;
import pec.*;
import population.Individual;
import population.Population;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class Simulation {
    static int mu, delta, rho, colms, rows, comfortSens, maxEdgeCost;
    static Point initialPoint, finalPoint;
    private static int initialPop, maxPop, finalInst;
    private static HashMap<List<Point>, Integer> specialCostZones;
    private static List<Point> obstacles;
    private static Parser parser;
    private static Population population;
    private static Map map;
    private static PEC pec;

    public Simulation(String filePath) throws IOException, SAXException, ParserConfigurationException {
        parseFile(filePath);
        map = new Map(colms, rows, obstacles, specialCostZones, finalPoint);
        maxEdgeCost = map.edgeMaxCost();
        population = new Population(initialPop);
        pec = new PEC(finalInst);
    }

    // retira toda a informaçao necessario do ficheiro XML
    private static void parseFile(String filePath) throws ParserConfigurationException, SAXException, IOException {
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

    private static void createMap() {
        map.createGrid();
    }

    private static void createAndAddPrintEvents() {
        Event printEvent;
        double timeInterval = (double) finalInst / 20;
        for (double t = timeInterval; t <= finalInst; t++) {
            if (t % timeInterval == 0) {
                printEvent = new EventPrint(t);
                printEvent.addToPec();
            }
        }
    }

    private static void generateInitialEvents(Individual individual) {
        Move m = new Move(QuickMaths.moveParameter(individual.getComfort()));
        Reproduction r = new Reproduction(QuickMaths.reproductionParameter(individual.getComfort()));
        Death d = new Death(QuickMaths.deathParameter(individual.getComfort()));

        d.setHost(individual);
        m.setHost(individual);
        r.setHost(individual);

        d.addToPec();
        m.addToPec();
        r.addToPec();
    }

    private static void initializeSimulation() {
        //creates map
        createMap();

        for (int ind = 0; ind < population.size; ind++) {
            Individual individual = population.individuals.get(ind);
            individual.setPosition(initialPoint);
            individual.addToPath(initialPoint);
            individual.addToCostPath(0);
            individual.setComfort(QuickMaths.calculateComfort(individual));

            generateInitialEvents(individual);
        }

        //creates print events and adds them to pec
        createAndAddPrintEvents();
    }

    private static void epidemic() {
        if (Population.size >= maxPop) {
            //sorts individuals from best comfort to worst comfort
            sort(Population.individuals);

            //creates the list of individuals that survive for sure
            List<Individual> survivors = new ArrayList<>();
            for (int s = 0; s < 5; s++) {
                survivors.add(Population.individuals.get(s));
            }

            //epidemic strikes!
            for (int i = 0; i < Population.size; i++) {
                if (!survivors.contains(Population.individuals.get(i)) && (Population.individuals.get(i).getComfort() <= Math.random())) {
                    Population.killIndividual(Population.individuals.get(i));
                    i--;
                }
            }
        }
    }

    //helps to sort the individuals by comfort in order to choose the first 5 when/if the epidemic strikes.
    private static void sort(List<Individual> individuals) {
        Collections.sort(individuals, (i1, i2) -> {
            double comf1 = i1.getComfort();
            double comf2 = i2.getComfort();
            return Double.compare(comf2, comf1);
        });
    }

    // simula
    public static void simulate() {
        initializeSimulation();

        while (PEC.eventQueue.size() != 0) {
            epidemic();
            pec.executeEvent();
        }

        parser.printResult(Population.bestPath);
    }
}