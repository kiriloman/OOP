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
    	return (-(1 - Math.log(comfort) * delta) * Math.log(1 - random.nextDouble()));
    }
    
    public static double deathParameter(double comfort, int mu, Random random ){
    	return (-(1 - Math.log(1 - comfort) * mu) * Math.log(1 - random.nextDouble()));
    }
    
    public static double reproductionParameter(double comfort, int rho, Random random ){
    	return -(1 - Math.log(comfort) * rho) * Math.log(1 - random.nextDouble());
    }
    
    public static double calculateComfort(Individual hst){
    	return ((double) Math.pow(1 - (hst.getCost() - QuickMaths.length(hst) + 2) / ((Simulation.cmax - 1) * (QuickMaths.length(hst)) + 3), Simulation.comfortSens)) * Math.pow(1 - ((double) QuickMaths.dist(hst) / ((double) Simulation.colms + Simulation.rows + 1)), Simulation.comfortSens);
    }
}
