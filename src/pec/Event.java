package pec;

import maths.QuickMaths;
import population.Individual;
import population.Population;

/**
 * Represents an event.
 */
public class Event {
    /**
     * Execution time of the event.
     */
    protected double time;
    /**
     * PEC to add event to.
     */
    protected PEC pec;
    /**
     * Host's population.
     */
    protected Population population;

    /**
     * Used for calculating comforts and times.
     */
    protected QuickMaths quickMaths;

    /**
     * Event constructor.
     * @param time Execution time
     * @param population Population host belongs to
     * @param pec PEC to add event to
     */
    Event(double time, Population population, PEC pec) {
        this.time = time;
        this.population = population;
        this.pec = pec;
        quickMaths = new QuickMaths();
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
        pec.addEvent(this);
    }

    /**
     * Gets event's execution time
     * @return Execution time
     */
    public double getTime(){
        return time;
    }
}
