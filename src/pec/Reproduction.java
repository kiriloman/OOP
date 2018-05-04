package pec;

import population.Individual;

// Evento Reproduction tem um host que é o individuo a quem o evento ta ligado e tem tempo que
// é o tempo de execuçao.
// Como instanciar? Execute fica vazio? E o rho?
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
