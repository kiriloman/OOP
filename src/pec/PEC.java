package pec;

import population.Individual;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

// Classe PEC. Tem uma eventQueue e adiçao de eventos a eventQueue.
public class PEC {
    public List<Event> eventQueue;

    public PEC() {
        eventQueue = new ArrayList<>();
    }

    public void addEvent(Event event) {
        // se a queue tiver vazia adiciona logo o evento
        if (eventQueue.size() == 0) {
            eventQueue.add(event);
            return;
        }
        if (event instanceof EventInd) {
            Individual host = ((EventInd) event).getHost();
            for (int i = 0; i < eventQueue.size(); i++) {
                if (eventQueue.get(i).getTime() < event.getTime()) {
                    // Verifica se a Morte dele nao esta a frente, se sim nao adicionamos evento
                    if (eventQueue.get(i) instanceof EventInd) {
                        if (host.equals(((EventInd) eventQueue.get(i)).getHost()) && eventQueue.get(i) instanceof Death)
                            return;
                    }
                } else {
                    eventQueue.add(i, event);
                    return;
                }
            }
            // adiciona evento caso todos os tempos sao menores e ele tem de ir para o fim da queue
            eventQueue.add(event);
        } else {
            for (int i = 0; i < eventQueue.size(); i++) {
                if (eventQueue.get(i).getTime() >= event.getTime()) {
                    eventQueue.add(i, event);
                    return;
                }
            }
            // adiciona evento caso todos os tempos sao menores e ele tem de ir para o fim da queue
            eventQueue.add(event);
        }
    }

    // se a eventqueue tiver vazia, manda exception
    public Event getEvent() throws ArrayIndexOutOfBoundsException {
        Event event = eventQueue.get(0);
        eventQueue.remove(0);
        return event;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < eventQueue.size(); i++) {
            str += "(" + eventQueue.get(i).getClass().getSimpleName() + ", " + ((EventInd)eventQueue.get(i)).getHost().getId() + ") ";
        }
        return str;
    }
}
