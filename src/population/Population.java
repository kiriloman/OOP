package population;

import grid.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pec.EventInd;
import pec.PEC;

//Classe Population com cenas basicas
public class Population {
    public static List<Individual> individuals;
    public static int size;
    public static int nextChildId;

    public Population(int numberOfIndividuals) {
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

    public void killIndividual(PEC pec, Individual individual) {
        removeIndividual(individual);
        for (int i = 0; i < pec.eventQueue.size(); i++) {
            if (pec.eventQueue.get(i) instanceof EventInd) {
                if (((EventInd) pec.eventQueue.get(i)).getHost().equals(individual)) {
                    pec.eventQueue.remove(i);
                    i--;
                }
            }
        }
    }
}