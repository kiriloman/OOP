package pec;

import population.Population;

public class Death extends EventInd {

    public Death(double time) {
        super(time);
    }

    @Override
    public void addToPec() {
        this.getHost().setDeathTime(this.getTime());
        PEC.addEvent(this);
    }

    @Override
    public void execute() {
        Population.removeIndividual(this.getHost());
    }
}
