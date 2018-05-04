package pec;

import population.Individual;

public class EventInd extends Event {
    private Individual host;

    public EventInd(double time) {
        super(time);
    }

    public void setHost(Individual host) {
        this.host = host;
    }

    public Individual getHost() {
        return host;
    }
}
