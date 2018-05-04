package population;

import java.util.ArrayList;
import java.util.List;

//Classe Population com cenas basicas
public class Population {
    public List<Individual> individuals;
    public int size;

    public Population(int numOfIndividuals) {
        size = numOfIndividuals;
        individuals = new ArrayList<>();
        for (int i = 0; i < numOfIndividuals; i++) {
            individuals.add(new Individual(i));
        }
    }

    public void addIndividual(Individual individual) {
        individuals.add(individual);
        size++;
    }
}
