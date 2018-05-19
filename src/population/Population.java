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
    public List<Individual> individuals;
    /**
     * Number of individuals.
     */
    public int size;
    /**
     * Id of next child to be added to population.
     */
    public int nextChildId;
    /**
     * Current best path between all individuals.
     */
    public List<Point> bestPath;
    /**
     * Current best path's cost.
     */
    public double bestPathCost;
    /**
     * Individual's, whos path is the current best path, comfort.
     */
    public double bestPathComfort;
    /**
     * Informs if final point has been hit by some path or not.
     */
    public boolean finalPointHit;
    /**
     * Final point that should be reached.
     */
    public Point finalPoint;

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
    public void addIndividual(Individual individual) {
        individuals.add(individual);
        size++;
        nextChildId++;
    }

    /**
     * Removes a given individual from population.
     * @param individual Individual to remove
     */
    public void removeIndividual(Individual individual) {
        individuals.remove(individual);
        size--;
    }

    /**
     * Removes a given individual from population and also
     * removes all its events from PEC.
     * @param individual Individual to kill
     * @param pec PEC from which all individual's events will be removed
     */
    public void killIndividual(Individual individual, PEC pec) {
        removeIndividual(individual);
        for (int i = 0; i < pec.eventQueue.size(); i++) {
            pec.eventQueue.get(i).removeIfItsHostDies(individual);
        }
    }

    /**
     * Sets final point individuals should reach.
     * @param finalPoint A final point
     */
    public void setFinalPoint(Point finalPoint) {
        this.finalPoint = finalPoint;
    }
}