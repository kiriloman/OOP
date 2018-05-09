package pec;

import population.Individual;

/**
 * Represents an event that has an Individual that is its host.
 */
public class EventInd extends Event {
    /**
     * Event's host.
     */
    private Individual host;

    /**
     * EventInd constructor.
     * @param time Execution time
     */
    EventInd(double time) {
        super(time);
    }

    /**
     * Removes this event from PEC if its host has died.
     * @param individual Host of the event.
     */
    @Override
    public void removeIfItsHostDies(Individual individual) {
        if (this.getHost().equals(individual)) {
            PEC.eventQueue.remove(this);
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
