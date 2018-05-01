package ist.oop.project;

import java.util.ArrayList;
import java.util.List;

public class PEC {
    public List<Event> eventQueue;

    PEC() {
        eventQueue = new ArrayList<>();
    }

    public void addEvent(Event event) {
        Individual host = event.getHost();
        for (int i = 0; i < eventQueue.size(); i++) {
            if (eventQueue.get(i).getTime() < event.getTime()) {
                if (host.equals(eventQueue.get(i).getHost()) && eventQueue.get(i) instanceof Death)
                    return;
            } else {
                eventQueue.add(i, event);
                return;
            }
        }
    }
}
