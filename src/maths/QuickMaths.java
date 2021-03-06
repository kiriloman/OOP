package maths;

import java.util.Random;

import population.Individual;
import simulation.Simulation;

/**
 * Represents several calculations used in simulation.
 */
public class QuickMaths {
    /**
     * Calculates distance from individual's position to final point.
     * @param z Individual
     * @return Distance from individual's position to final point
     */
    private int dist(Individual z) {
        return Math.abs(z.getPosition().getX() - Simulation.finalPoint.getX()) + Math.abs(z.getPosition().getY() - Simulation.finalPoint.getY());
    }

    /**
     * Calculates number of edges traversed by an individual.
     * @param z Individual
     * @return Size of individual's path
     */
    private int length(Individual z) {
        if (z.getPath() == null) {
            return 0;
        } else {
            return z.getPath().size();
        }
    }

    /**
     * Calculates time between move events given some comfort.
     * @param comfort Comfort
     * @return Time until next move event
     */
    public double moveParameter(double comfort){
        Random random = new Random();
    	return -((1 - Math.log(comfort)) * Simulation.delta) * Math.log(1 - random.nextDouble());
    }

    /**
     * Calculates time of death event given some comfort.
     * @param comfort Comfort
     * @return Time of death event
     */
    public double deathParameter(double comfort){
        Random random = new Random();
    	return -((1 - Math.log(1 - comfort)) * Simulation.mu) * Math.log(1 - random.nextDouble());
    }

    /**
     * Calculates time between reproduction events given some comfort.
     * @param comfort Comfort
     * @return Time until next reproduction event
     */
    public double reproductionParameter(double comfort){
        Random random = new Random();
    	return -((1 - Math.log(comfort)) * Simulation.rho) * Math.log(1 - random.nextDouble());
    }

    /**
     * Calculates individual's comfort
     * @param hst Individual to calculate comfort for
     * @return Comfort of given individual
     */
    public double calculateComfort(Individual hst){
    	return (Math.pow(1 - ((double) hst.getCost() - (double) length(hst) + (double) 2) / (((double) Simulation.maxEdgeCost - (double) 1) * ((double) length(hst)) + (double) 3), (double) Simulation.comfortSens)) * Math.pow((double) 1 - ((double) dist(hst) / ((double) Simulation.colms + (double) Simulation.rows + (double) 1)), (double) Simulation.comfortSens);
    }
}
