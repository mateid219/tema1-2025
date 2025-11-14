package simulation.events;

import simulation.Simulation;
import simulation.Cell;
import entities.Plants.Plant;

import java.util.Queue;

public class SoilEvent extends Event {

    Cell cell;

    private static final String DEAD = "dead";
    private static final double PLANT_GROWTH = 0.2;

    public SoilEvent() { }
    public SoilEvent(int timestamp, Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = EVENT_PRIORITIES.SOIL_EVENT.ordinal();
    }
    public void takeEffect(Simulation simulation) {
        Queue<Event> eventQueue = simulation.getEventQueue();
        Plant plant = cell.getPlant();
        if (plant == null) {
            return;
        }
        plant.grow(PLANT_GROWTH);
        if (DEAD.equals(plant.getMaturity())) {
            cell.setPlant(null);
        } else {
            eventQueue.add(new SoilEvent(timestamp + 1, cell));
        }
    }
}
