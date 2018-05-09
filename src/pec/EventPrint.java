package pec;

import population.Population;

/**
 * Represents an observation event with no host.
 */
public class EventPrint extends Event {
    /**
     * Number of observations.
     */
    private static int observationNum;

    /**
     * EventPrint constructor.
     * @param time Execution time
     */
    public EventPrint(double time) {
        super(time);
        observationNum = 0;
    }

    /**
     * Prints out data of an observation.
     */
    @Override
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
