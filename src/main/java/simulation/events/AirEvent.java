package simulation.events;

import simulation.Simulation;
import simulation.Cell;
import simulation.terrabot.TerraBot;
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
        priority = EVENT_PRIORITIES.AIR_EVENT.ordinal();
    }

    public void takeEffect(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        Queue<Event> eventQueue = simulation.getEventQueue();
        Air air = cell.getAir();
        Animal animal = cell.getAnimal();
        if (animal == null || ! animal.isScanned()) {
            return;
        }
        if (air.isToxic()) {
            animal.setState(SICK);
        }
        eventQueue.add(new AirEvent(timestamp + 1, cell));
    }
}
