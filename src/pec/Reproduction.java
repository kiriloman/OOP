package pec;

import population.Individual;
import population.Population;

public class Reproduction extends EventInd {

    public Reproduction(double time) {
        super(time);
    }

    @Override
    public void execute() {
        System.out.println("Reproduction");
        Population.addIndividual(new Individual(Population.nextChildId));
    }
}
