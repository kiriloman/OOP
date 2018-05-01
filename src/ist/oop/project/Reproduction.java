package ist.oop.project;

public class Reproduction implements Event {
    private int rho; //?
    private double time;
    private Individual host;

    Reproduction(Individual host, int rho) { //?
        this.host = host;
        this.rho = rho;
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
