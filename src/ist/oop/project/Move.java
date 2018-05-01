package ist.oop.project;

// Evento Move tem um host que é o individuo a quem o evento ta ligado e tem tempo que
// é o tempo de execuçao.
// Como instanciar? Execute fica vazio? E o delta?
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
