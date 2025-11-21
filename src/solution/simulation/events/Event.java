package simulation.events;

import simulation.Simulation;
import lombok.Getter;
import lombok.Setter;

public abstract class Event implements Comparable<Event> {

    @Getter @Setter protected int timestamp;
    @Getter protected int priority;


    public Event() { }
    public Event(final int timestamp) {
        this.timestamp = timestamp;
        priority = EventPriorities.NONE.ordinal();
    }
    @Override
    public final int compareTo(final Event o) {
        if (timestamp == o.timestamp) {
            return priority - o.priority;
        }
        return timestamp - o.timestamp;
    }
    /**
     * Executes an event that takes place at multiple moments in time.
     * Uses {@link Simulation#addEvent(Event)} to add future events.
     * Event chronology is dictated by {@link #compareTo(Event)}.
     */
    public abstract void takeEffect(Simulation simulation);
}
