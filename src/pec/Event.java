package pec;

import population.Individual;

public class Event {
    private double time;

    Event(double time) {
        this.time = time;
    }

    public void execute() {

    }

    public void removeIfItsHostDies(Individual individual) {

    }

    public void addToPec() {
        PEC.addEvent(this);
    }

    public double getTime(){
        return time;
    }
}
