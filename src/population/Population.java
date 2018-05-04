package population;

import java.util.ArrayList;
import java.util.List;

//Classe Population com cenas basicas
public class Population {
    public static List<Individual> individuals;
    public static int size;
    public static int nextChildId;

    public Population(int numberOfIndividuals) {
        size = numberOfIndividuals;
        individuals = new ArrayList<>();
        nextChildId = numberOfIndividuals;
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
}
