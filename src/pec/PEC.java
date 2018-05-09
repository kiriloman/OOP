package pec;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a PEC.
 */
public class PEC {
    /**
     * Final instant of simulation until which events can occur.
     */
    private static int finalInstant;
    /**
     * Represents a queue of events ordered by their execution times.
     */
    public static List<Event> eventQueue;
    /**
     * Number of events already executed.
     */
    public static int numberOfEvents;

    /**
     * PEC constructor.
     * @param finalInstant Instant until which events can occur
     */
    public PEC(int finalInstant) {
        PEC.finalInstant = finalInstant;
        eventQueue = new ArrayList<>();
        numberOfEvents = 0;
    }

    /**
     * Adds given event to the event queue.
     * @param event An event to add
     */
    public static void addEvent(Event event) {
        // Checks if event's execution time is lower or equal to final instant time
        if (event.getTime() <= finalInstant) {
            // If the event queue is empty, add event to queue
            if (eventQueue.size() == 0) {
                eventQueue.add(event);
                return;
            }
            // Finds a spot in queue to add the event.
            // This spot depends on event's execution time
            for (int i = 0; i < eventQueue.size(); i++) {
                if (eventQueue.get(i).getTime() >= event.getTime()) {
                    eventQueue.add(i, event);
                    return;
                }
            }
            // If no spot has been found then add event at the end of the queue.
            eventQueue.add(event);
        }
    }

    /**
     * Executes the first event of the queue removing it from the queue after.
     */
    public void executeEvent() {
        Event event = eventQueue.get(0);
        event.execute();
        eventQueue.remove(0);
        numberOfEvents++;
    }
}
