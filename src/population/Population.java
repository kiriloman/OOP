package population;

import grid.Point;

import java.util.ArrayList;
import java.util.List;

import pec.EventInd;
import pec.PEC;

public class Population {
    public static List<Individual> individuals;
    public static int size;
    public static int nextChildId;
    public static List<Point> bestPath;
    public static double bestPathCost, bestPathComfort;
    public static boolean finalPointHit;

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

    public static void addIndividual(Individual individual) {
        individuals.add(individual);
        size++;
        nextChildId++;
    }

    public static void removeIndividual(Individual individual) {
        individuals.remove(individual);
        size--;
    }

    public static void killIndividual(Individual individual) {
        removeIndividual(individual);
        for (int i = 0; i < PEC.eventQueue.size(); i++) {
            if (PEC.eventQueue.get(i) instanceof EventInd) {
                if (((EventInd) PEC.eventQueue.get(i)).getHost().equals(individual)) {
                    PEC.eventQueue.remove(i);
                    i--;
                }
            }
        }
    }
}