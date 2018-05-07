package pec;

import population.Population;

public class EventPrint extends Event {
    private static int observationNum;

    public EventPrint(double time) {
        super(time);
        observationNum = 0;
    }
    
    public void execute() {
        observationNum++;
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "Observation " + observationNum + ":", "", ""));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Present instant:", this.getTime()));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Number of realized events:", PEC.numberOfEvents));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Population size:", Population.size));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Final point has been hit:", Population.finalPointHit));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Path of the best fit individual:", Population.bestPath.toString().replace("[", "{").replace("]", "}")));
        if (Population.finalPointHit)
            System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Cost:", (int) Population.bestPathCost));
        else
            System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Comfort:", Population.bestPathComfort));
    }
}
