package ist.oop.project;

public class Move implements Event {
    private int delta;

    Move(int delta) {
        this.delta = delta;
    }

    @Override
    public void execute(double comfort) {

    }
}
