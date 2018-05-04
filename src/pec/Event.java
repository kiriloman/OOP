package pec;

import population.Individual;

// interface dos Eventos.
// como definir execute?
public interface Event {
    void execute(double comfort); // ?
    double getTime();
    Individual getHost();
}
