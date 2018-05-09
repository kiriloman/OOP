package pec;

import population.Population;

/**
 * Represents a death event of an Individual.
 */
public class Death extends EventInd {

    /**
     * Death constructor.
     * @param time Execution time
     */
    public Death(double time) {
        super(time);
    }

    /**
     * Sets its host death time to this event's execution time.
     * Adds this event to PEC.
     */
    @Override
    public void addToPec() {
        this.getHost().setDeathTime(this.getTime());
        PEC.addEvent(this);
    }

    /**
     * Removes its host from the Population.
     */
    @Override
    public void execute() {
        Population.removeIndividual(this.getHost());
    }
}
