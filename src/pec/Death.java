package pec;

import population.Population;

/**
 * Represents a death event of an Individual.
 */
public class Death extends EventInd {
    /**
     * Death constructor.
     * @param time Execution time
     * @param population Population host belongs to
     * @param pec PEC to add event to
     */
    public Death(double time, Population population, PEC pec) {
        super(time, population, pec);
    }

    /**
     * Sets its host death time to this event's execution time and adds this event to PEC.
     */
    @Override
    public void addToPec() {
        this.getHost().setDeathTime(this.getTime());
        pec.addEvent(this);
    }

    /**
     * Removes its host from the Population.
     */
    @Override
    public void execute() {
        population.removeIndividual(host);
    }
}
