package pec;

import population.Individual;
import population.Population;

/**
 * Represents an event that has an Individual that is its host.
 */
public class EventInd extends Event {
    /**
     * Event's host.
     */
    protected Individual host;

    /**
     * EventInd constructor.
     * @param time Execution time
     * @param population Population host belongs to
     * @param pec PEC to add event to
     */
    EventInd(double time, Population population, PEC pec) {
        super(time, population, pec);
    }

    /**
     * Removes this event from PEC if its host has died.
     * @param individual Host of the event.
     */
    @Override
    public void removeIfItsHostDies(Individual individual) {
        if (this.getHost().equals(individual)) {
            pec.eventQueue.remove(this);
        }
    }

    /**
     * Sets the host.
     * @param host New host
     */
    public void setHost(Individual host) {
        this.host = host;
    }

    /**
     * Gets the host.
     * @return Host
     */
    public Individual getHost() {
        return host;
    }
}
