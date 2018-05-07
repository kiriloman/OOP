package pec;

import population.Population;
import simulation.Simulation;

public class EventPrint extends Event {

    public EventPrint(double time) {
        super(time);
    }

    public void execute() {
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "Observation " + Simulation.observationNum + ":", "", ""));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Present instant:", (int) Simulation.instant));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Number of realized events:", Simulation.numOfEvents));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Population size:", Population.size));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Final point has been hit:", Simulation.finalPointHit));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Path of the best fit individual:", Simulation.bestPath.toString().replace("[", "{").replace("]", "}")));
        if (Simulation.finalPointHit)
            System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Cost:", (int) Simulation.bestPathCost));
        else
            System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Comfort:", Simulation.bestPathComfort));
    }
}
