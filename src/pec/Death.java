package pec;

import population.Population;

public class Death extends EventInd {

    public Death(double time) {
        super(time);
    }

    @Override
    public void execute() {
        System.out.println("Died");
        Population.removeIndividual(this.getHost());
    }
}
