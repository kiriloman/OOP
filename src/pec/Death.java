package pec;

import population.Individual;

// Evento Death tem um host que é o individuo a quem o evento ta ligado e tem tempo que
// é o tempo de execuçao.
// Como instanciar? Execute fica vazio? E o mu?
public class Death implements Event {
    private int mu; //?
    private Individual host;
    private double time;

    Death(Individual host, int mu, double time) { //?
        this.host = host;
        this.mu = mu;
        this.time = time;
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
