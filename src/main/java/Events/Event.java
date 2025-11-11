package Events;

import Simulation.Simulation;
import lombok.Getter;

public abstract class Event implements Comparable<Event> {

    @Getter protected int timestamp;
    @Getter protected int priority;

    protected static final int AIR_EVENT_PRIORITY = 6;
    protected static final int SOIL_EVENT_PRIORITY = 5;
    protected static final int WATER_EVENT_PRIORITY = 4;
    protected static final int PLANT_EVENT_PRIORITY = 3;
    protected static final int ANIMAL_FEED_PRIORITY = 2;
    protected static final int ANIMAL_MOVE_PRIORITY = 1;

    private static final int NO_PRIORITY = 0;

    public Event() { }
    public Event(int timestamp) {
        this.timestamp = timestamp;
        priority = NO_PRIORITY;
    }
    public int compareTo(Event o) {
        if (timestamp == o.timestamp) {
            return o.priority - priority;
        }
        return timestamp - o.timestamp;
    }
    public abstract void takeEffect(Simulation simulation);
}
