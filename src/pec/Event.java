package pec;

import population.Individual;

/**
 * Represents an event.
 */
public class Event {
    /**
     * Execution time of the event.
     */
    private double time;

    /**
     * Event constructor.
     * @param time Execution time
     */
    Event(double time) {
        this.time = time;
    }

    /**
     * Executes the event.
     */
    public void execute() {

    }

    /**
     * Removes this event from PEC if its host dies.
     * @param individual Host of the event.
     */
    public void removeIfItsHostDies(Individual individual) {

    }

    /**
     * Adds this event to PEC.
     */
    public void addToPec() {
        PEC.addEvent(this);
    }

    /**
     * Gets event's execution time
     * @return Execution time
     */
    public double getTime(){
        return time;
    }
}
