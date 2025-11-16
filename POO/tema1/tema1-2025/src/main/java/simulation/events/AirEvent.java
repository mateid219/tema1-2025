package simulation.events;

import entities.animals.Animal;
import entities.air.Air;
import lombok.NoArgsConstructor;
import simulation.Cell;
import simulation.Simulation;

import java.util.Queue;
@NoArgsConstructor
public final class AirEvent extends Event {
    private Cell cell;

    private static final String SICK = "sick";

    public AirEvent(final int timestamp, final Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = EventPriorities.AIR_EVENT.ordinal();
    }
    @Override
    public void takeEffect(final Simulation simulation) {
        Queue<Event> eventQueue = simulation.getEventQueue();
        Air air = cell.getAir();
        Animal animal = cell.getAnimal();
        if (animal == null || !animal.isScanned()) {
            return;
        }
        if (air.isToxic()) {
            animal.setState(SICK);
        }
        eventQueue.add(new AirEvent(timestamp + 1, cell));
    }
}
