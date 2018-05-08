package pec;

import java.util.ArrayList;
import java.util.List;

// Classe PEC. Tem uma eventQueue e adiçao de eventos a eventQueue.
public class PEC {
    private static int finalInstant;
    public static List<Event> eventQueue;
    public static int numberOfEvents;

    public PEC(int finalInstant) {
        PEC.finalInstant = finalInstant;
        eventQueue = new ArrayList<>();
        numberOfEvents = 0;
    }

    public static void addEvent(Event event) {
        //verificar se o tempo do evento e menor ou igual a finalInst
        if (event.getTime() <= finalInstant) {
            // se a queue tiver vazia adiciona logo o evento
            if (eventQueue.size() == 0) {
                eventQueue.add(event);
                return;
            }
            //ver onde podemos adicionar o evento
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

    //execute o evento
    public void executeEvent() {
        Event event = eventQueue.get(0);
        event.execute();
        eventQueue.remove(0);
        numberOfEvents++;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < eventQueue.size(); i++) {
            str += "(" + eventQueue.get(i).getClass().getSimpleName() + ", " + ((EventInd) eventQueue.get(i)).getHost().getId() + ") ";
        }
        return str;
    }
}
