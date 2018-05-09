package simulation;

import grid.Map;
import grid.Point;
import org.xml.sax.SAXException;
import pec.*;
import population.Individual;
import population.Population;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a Simulation.
 */
public class Simulation {
    /**
     * Parameter used in death event.
     */
    static int mu;
    /**
     * Parameter used in move event.
     */
    static int delta;
    /**
     * Parameter used in reproduction event.
     */
    static int rho;
    /**
     * Number of columns of the grid the simulation runs on.
     */
    static int colms;
    /**
     * Number of rows of the grid the simulation runs on.
     */
    static int rows;
    /**
     * Comfort sensitivity.
     */
    static int comfortSens;
    /**
     * Maximum cost of an edge of the grid the simulation runs on.
     */
    static int maxEdgeCost;
    /**
     * Initial point of the grid the simulation runs on.
     */
    static Point initialPoint;
    /**
     * Final point of the grid the simulation runs on.
     */
    static Point finalPoint;
    /**
     * Initial size of the population that simulation is using.
     */
    private static int initialPop;
    /**
     * Maximum size of the population that simulation is using.
     */
    private static int maxPop;
    /**
     * Simulation's final instant.
     */
    private static int finalInst;
    /**
     * Zones with special costs of the grid the simulation runs on.
     */
    private static HashMap<List<Point>, Integer> specialCostZones;
    /**
     * Obstacles of the grid the simulation runs on.
     */
    private static List<Point> obstacles;
    /**
     * A parser used to parse a file before simulation.
     */
    private static Parser parser;
    /**
     * Population that simulations is using.
     */
    private static Population population;
    /**
     * Map that simulations is using.
     */
    private static Map map;
    /**
     * PEC that simulations is using.
     */
    private static PEC pec;

    /**
     * Simulation constructor.
     * @param filePath Path to .xml file
     * @throws IOException IO exception
     * @throws SAXException Parser exception
     * @throws ParserConfigurationException Parser exception
     */
    public Simulation(String filePath) throws IOException, SAXException, ParserConfigurationException {
        parseFile(filePath);
        map = new Map(colms, rows, obstacles, specialCostZones, finalPoint);
        maxEdgeCost = map.getEdgesMaxCost();
        population = new Population(initialPop);
        pec = new PEC(finalInst);
    }

    /**
     * Parses given file and assigns values to necessary parameters in simulation.
     * @param filePath Path to .xml file
     * @throws ParserConfigurationException Parser exception
     * @throws SAXException Parser exception
     * @throws IOException IO exception
     */
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

    /**
     * Creates a map.
     */
    private static void createMap() {
        map.createGrid();
    }

    /**
     * Creates observation events and adds them to PEC.
     */
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

    /**
     * Generates initial events for a given individual and adds them to PEC.
     * @param individual Individual
     */
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

    /**
     * Initializes simulation by creating a new Map, Population, PEC and events.
     */
    private static void initializeSimulation() {
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

    /**
     * Partially destroys the population if population size is bigger or equal
     * to its maximum size.
     */
    private static void epidemic() {
        if (Population.size >= maxPop) {
            // Sorts individuals from best comfort to worst comfort
            sort(Population.individuals);

            // Creates the list of individuals that survive for sure
            List<Individual> survivors = new ArrayList<>();
            for (int s = 0; s < 5; s++) {
                survivors.add(Population.individuals.get(s));
            }

            // Epidemic strikes!
            for (int i = 0; i < Population.size; i++) {
                if (!survivors.contains(Population.individuals.get(i)) && (Population.individuals.get(i).getComfort() <= Math.random())) {
                    Population.killIndividual(Population.individuals.get(i));
                    i--;
                }
            }
        }
    }

    /**
     * Sorts a list of individuals by comfort, from highest to lowest.
     * @param individuals List of individuals to sort
     */
    private static void sort(List<Individual> individuals) {
        Collections.sort(individuals, (i1, i2) -> {
            double comf1 = i1.getComfort();
            double comf2 = i2.getComfort();
            return Double.compare(comf2, comf1);
        });
    }

    /**
     * Simulates.
     */
    public static void simulate() {
        initializeSimulation();

        while (PEC.eventQueue.size() != 0) {
            epidemic();
            pec.executeEvent();
        }

        parser.printResult(Population.bestPath);
    }
}