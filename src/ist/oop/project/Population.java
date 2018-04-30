package ist.oop.project;

import java.util.ArrayList;
import java.util.List;

public class Population {
    List<Individual> individuals;

    Population(int numOfIndividuals) {
        individuals = new ArrayList<>();
        for (int i = 0; i < numOfIndividuals; i++) {
            individuals.add(new Individual());
        }
    }
}
