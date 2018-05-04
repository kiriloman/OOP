package pec;

import grid.Point;

import java.util.List;

public class EventPrint extends Event {

    public EventPrint(double time) {
        super(time);
    }

    public void execute(int obsN, int instant, int events, int size, boolean hitFinalP, List<Point> path, int cost, double comfort) {
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "Observation " + obsN + ":", "", ""));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Present instant:", instant));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Number of realized events:", events));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Population size:", size));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Final point has been hit:", hitFinalP));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Path of the best fit individual:", path.toString().replace("[", "{").replace("]", "}")));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Cost/Comfort:", (double) cost/comfort));
    }
}
