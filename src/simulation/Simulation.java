package simulation;

import grid.Map;
import grid.Point;
import maths.QuickMaths;
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
    public static int mu;
    /**
     * Parameter used in move event.
     */
    public static int delta;
    /**
     * Parameter used in reproduction event.
     */
    public static int rho;
    /**
     * Number of columns of the grid the simulation runs on.
     */
    public static int colms;
    /**
     * Number of rows of the grid the simulation runs on.
     */
    public static int rows;
    /**
     * Comfort sensitivity.
     */
    public static int comfortSens;
    /**
     * Maximum cost of an edge of the grid the simulation runs on.
     */
    public static int maxEdgeCost;
    /**
     * Initial point of the grid the simulation runs on.
     */
    private Point initialPoint;
    /**
     * Final point of the grid the simulation runs on.
     */
    public static Point finalPoint;
    /**
     * Initial size of the population that simulation is using.
     */
    private int initialPop;
    /**
     * Maximum size of the population that simulation is using.
     */
    private int maxPop;
    /**
     * Simulation's final instant.
     */
    private int finalInst;
    /**
     * Zones with special costs of the grid the simulation runs on.
     */
    private HashMap<List<Point>, Integer> specialCostZones;
    /**
     * Obstacles of the grid the simulation runs on.
     */
    private List<Point> obstacles;
    /**
     * A parser used to parse a file before simulation.
     */
    private Parser parser;
    /**
     * Population that simulations is using.
     */
    public Population population;
    /**
     * Map that simulations is using.
     */
    private Map map;
    /**
     * PEC that simulations is using.
     */
    private PEC pec;

    /**
     * QuickMaths that simulations is using.
     */
    private QuickMaths quickMaths;

    /**
     * Simulation constructor.
     * @param filePath Path to .xml file
     * @throws IOException IO exception
     * @throws SAXException Parser exception
     * @throws ParserConfigurationException Parser exception
     */
    public Simulation(String filePath) throws IOException, SAXException, ParserConfigurationException {
        parseFile(filePath);
        map = new Map(colms, rows, obstacles, specialCostZones);
        maxEdgeCost = map.getEdgesMaxCost();
        population = new Population(initialPop);
        pec = new PEC(finalInst);
        quickMaths = new QuickMaths();
    }

    /**
     * Parses given file and assigns values to necessary parameters in simulation.
     * @param filePath Path to .xml file
     * @throws ParserConfigurationException Parser exception
     * @throws SAXException Parser exception
     * @throws IOException IO exception
     */
    private void parseFile(String filePath) throws ParserConfigurationException, SAXException, IOException {
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
    private void createMap() {
        map.createGrid();
    }

    /**
     * Creates observation events and adds them to PEC.
     */
    private void createAndAddPrintEvents() {
        Event printEvent;
        double timeInterval = (double) finalInst / 20;
        for (double t = timeInterval; t <= finalInst; t++) {
            if (t % timeInterval == 0) {
                printEvent = new EventPrint(t, population, pec);
                printEvent.addToPec();
            }
        }
    }

    /**
     * Generates initial events for a given individual and adds them to PEC.
     * @param individual Individual
     */
    private void generateInitialEvents(Individual individual) {
        Move m = new Move(quickMaths.moveParameter(individual.getComfort()), population, pec);
        Reproduction r = new Reproduction(quickMaths.reproductionParameter(individual.getComfort()), population, pec);
        Death d = new Death(quickMaths.deathParameter(individual.getComfort()), population, pec);

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
    private void initializeSimulation() {
        createMap();
        population.setFinalPoint(finalPoint);
        for (int ind = 0; ind < population.size; ind++) {
            Individual individual = population.individuals.get(ind);
            individual.setPosition(initialPoint);
            individual.addToPath(initialPoint);
            individual.addToCostPath(0);
            individual.setComfort(quickMaths.calculateComfort(individual));

            generateInitialEvents(individual);
        }

        //creates print events and adds them to pec
        createAndAddPrintEvents();
    }

    /**
     * Partially destroys the population if population size is bigger or equal
     * to its maximum size.
     */
    private void epidemic() {
        if (population.size >= maxPop) {
            // Sorts individuals from best comfort to worst comfort
            sort(population.individuals);

            // Creates the list of individuals that survive for sure
            List<Individual> survivors = new ArrayList<>();
            for (int s = 0; s < 5; s++) {
                survivors.add(population.individuals.get(s));
            }

            // Epidemic strikes!
            for (int i = 0; i < population.size; i++) {
                if (!survivors.contains(population.individuals.get(i)) && (population.individuals.get(i).getComfort() <= Math.random())) {
                    population.killIndividual(population.individuals.get(i), pec);
                    i--;
                }
            }
        }
    }

    /**
     * Sorts a list of individuals by comfort, from highest to lowest.
     * @param individuals List of individuals to sort
     */
    private void sort(List<Individual> individuals) {
        Collections.sort(individuals, (i1, i2) -> {
            double comf1 = i1.getComfort();
            double comf2 = i2.getComfort();
            return Double.compare(comf2, comf1);
        });
    }

    /**
     * Simulates.
     */
    public void simulate() {
        initializeSimulation();

        while (pec.eventQueue.size() != 0) {
            epidemic();
            pec.executeEvent();
        }

        parser.printResult(population.bestPath);
    }
}