package ist.oop.project;

public class Death implements Event {
    private int mu;
    public double time;

    Death(int mu, double time) {
        this.mu = mu;
        this.time = time;
    }

    @Override
    public void execute(double comfort) {

    }
}
