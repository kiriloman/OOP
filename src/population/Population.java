package population;

import grid.Point;
import pec.PEC;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Population.
 */
public class Population {
    /**
     * List of individuals of population.
     */
    public static List<Individual> individuals;
    /**
     * Number of individuals.
     */
    public static int size;
    /**
     * Id of next child to be added to population.
     */
    public static int nextChildId;
    /**
     * Current best path between all individuals.
     */
    public static List<Point> bestPath;
    /**
     * Current best path's cost.
     */
    public static double bestPathCost;
    /**
     * Individual's, whos path is the current best path, comfort.
     */
    public static double bestPathComfort;
    /**
     * Informs if final point has been hit by some path or not.
     */
    public static boolean finalPointHit;

    /**
     * Population constructor.
     * @param numberOfIndividuals Number of individuals that population initially has
     */
    public Population(int numberOfIndividuals) {
        finalPointHit = false;
        bestPath = new ArrayList<>();
        bestPathCost = Integer.MIN_VALUE;
        bestPathComfort = 0;
        size = 0;
        individuals = new ArrayList<>();
        nextChildId = 0;
        for (int i = 0; i < numberOfIndividuals; i++) {
            addIndividual(new Individual(i));

        }
    }

    /**
     * Adds an individual to population.
     * @param individual Individual to add
     */
    public static void addIndividual(Individual individual) {
        individuals.add(individual);
        size++;
        nextChildId++;
    }

    /**
     * Removes a given individual from population.
     * @param individual Individual to remove
     */
    public static void removeIndividual(Individual individual) {
        individuals.remove(individual);
        size--;
    }

    /**
     * Removes a given individual from population and also
     * removes all its events from PEC.
     * @param individual Individual to kill
     */
    public static void killIndividual(Individual individual) {
        removeIndividual(individual);
        for (int i = 0; i < PEC.eventQueue.size(); i++) {
            PEC.eventQueue.get(i).removeIfItsHostDies(individual);
        }
    }
}