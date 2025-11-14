package simulation.events;

import simulation.Simulation;
import lombok.Getter;
import lombok.Setter;

public abstract class Event implements Comparable<Event> {

    @Getter @Setter protected int timestamp;
    @Getter protected int priority;

    public enum EVENT_PRIORITIES {
        AIR_EVENT,
        SOIL_EVENT,
        WATER_EVENT_INCREASE_STATS,
        WATER_EVENT_GROW_PLANT,
        PLANT_EVENT,
        ANIMAL_FERTILIZE,
        ANIMAL_FEED,
        ANIMAL_MOVE,
        ROBOT_EVENT,
        NONE
    }

    public Event() { }
    public Event(int timestamp) {
        this.timestamp = timestamp;
        priority = EVENT_PRIORITIES.NONE.ordinal();
    }
    public int compareTo(Event o) {
        if (timestamp == o.timestamp) {
            return priority - o.priority;
        }
        return timestamp - o.timestamp;
    }
    public abstract void takeEffect(Simulation simulation);
}
