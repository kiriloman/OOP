package ist.oop.project;

public class Move implements Event {
    private int delta; //?
    private double time;
    private Individual host;

    Move(Individual host, int delta) { //?
        this.host = host;
        this.delta = delta;
    }

    @Override
    public void execute(double comfort) {

    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public Individual getHost() {
        return host;
    }
}
