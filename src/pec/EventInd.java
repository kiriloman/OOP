package pec;

import population.Individual;

public class EventInd extends Event {
    private Individual host;

    EventInd(double time) {
        super(time);
    }

    @Override
    public void removeIfItsHostDies(Individual individual) {
        if (this.getHost().equals(individual)) {
            PEC.eventQueue.remove(this);
        }
    }

    public void setHost(Individual host) {
        this.host = host;
    }

    public Individual getHost() {
        return host;
    }
}
