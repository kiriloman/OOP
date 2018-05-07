package simulation;

import java.util.Random;

import grid.Point;
import population.Individual;

public class QuickMaths {
    

	 //dist function
    public static int dist(Individual z) {
        return Math.abs(z.getPosition().getX() - Simulation.finalPoint.getX()) + Math.abs(z.getPosition().getY() - Simulation.finalPoint.getY());
    }

    //length(z)-number of edges transversed by the individual z
    public static int length(Individual z) {
        if (z.getPath() == null) {
            return 0;
        } else {
            return z.getPath().size();
        }
    }
    
    public static double moveParameter(double comfort, int delta, Random random ){
    	return -((1 - Math.log(comfort)) * delta) * Math.log(1 - random.nextDouble());
    }
    
    public static double deathParameter(double comfort, int mu, Random random ){
    	return -((1 - Math.log(1 - comfort)) * mu) * Math.log(1 - random.nextDouble());
    }
    
    public static double reproductionParameter(double comfort, int rho, Random random ){
    	return -((1 - Math.log(comfort)) * rho) * Math.log(1 - random.nextDouble());
    }
    
    public static double calculateComfort(Individual hst){
    	return (Math.pow(1 - ((double) hst.getCost() - (double) QuickMaths.length(hst) + (double) 2) / (((double) Simulation.cmax - (double) 1) * ((double) QuickMaths.length(hst)) + (double) 3), (double) Simulation.comfortSens)) * Math.pow((double) 1 - ((double) QuickMaths.dist(hst) / ((double) Simulation.colms + (double) Simulation.rows + (double) 1)), (double) Simulation.comfortSens);
    }
}
