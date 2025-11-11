package Events;

import Simulation.Simulation;
import Simulation.Cell;
import entities.air.Air;
import entities.Animals.Animal;

import java.util.Queue;

public class AirEvent extends Event{
    Cell cell;

    private static final String SICK = "sick";

    public AirEvent() { }
    public AirEvent(int timestamp, Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = AIR_EVENT_PRIORITY;
    }

    public void takeEffect(Simulation simulation) {
        Air air = cell.getAir();
        Animal animal = cell.getAnimal();
        if (animal == null || ! animal.isScanned()) {
            return;
        }
        if (air.isToxic()) {
            animal.setState(SICK);
        }
        Queue<Event> eventQueue = simulation.getEventQueue();
        eventQueue.add(new AirEvent(timestamp + 1, cell));
    }
}
