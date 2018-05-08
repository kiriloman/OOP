package simulation;

import java.util.Random;

import population.Individual;

public class QuickMaths {
    //dist function
    private static int dist(Individual z) {
        return Math.abs(z.getPosition().getX() - Simulation.finalPoint.getX()) + Math.abs(z.getPosition().getY() - Simulation.finalPoint.getY());
    }

    //length(z)-number of edges transversed by the individual z
    private static int length(Individual z) {
        if (z.getPath() == null) {
            return 0;
        } else {
            return z.getPath().size();
        }
    }
    
    public static double moveParameter(double comfort){
        Random random = new Random();
    	return -((1 - Math.log(comfort)) * Simulation.delta) * Math.log(1 - random.nextDouble());
    }
    
    public static double deathParameter(double comfort){
        Random random = new Random();
    	return -((1 - Math.log(1 - comfort)) * Simulation.mu) * Math.log(1 - random.nextDouble());
    }
    
    public static double reproductionParameter(double comfort){
        Random random = new Random();
    	return -((1 - Math.log(comfort)) * Simulation.rho) * Math.log(1 - random.nextDouble());
    }
    
    public static double calculateComfort(Individual hst){
    	return (Math.pow(1 - ((double) hst.getCost() - (double) QuickMaths.length(hst) + (double) 2) / (((double) Simulation.maxEdgeCost - (double) 1) * ((double) QuickMaths.length(hst)) + (double) 3), (double) Simulation.comfortSens)) * Math.pow((double) 1 - ((double) QuickMaths.dist(hst) / ((double) Simulation.colms + (double) Simulation.rows + (double) 1)), (double) Simulation.comfortSens);
    }
}
