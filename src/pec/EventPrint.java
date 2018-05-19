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
     * @param population Population about which observation will be printed
     * @param pec PEC to add event to
     */
    public EventPrint(double time, Population population, PEC pec) {
        super(time, population, pec);
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
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Population size:", population.size));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Final point has been hit:", population.finalPointHit));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Path of the best fit individual:", population.bestPath.toString().replace("[", "{").replace("]", "}")));
        if (population.finalPointHit)
            System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Cost:", (int) population.bestPathCost));
        else
            System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Comfort:", population.bestPathComfort));
    }
}
