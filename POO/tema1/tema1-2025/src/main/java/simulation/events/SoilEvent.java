package simulation.events;

import simulation.Simulation;
import simulation.Cell;
import entities.Plants.Plant;

import java.util.Queue;

public final class SoilEvent extends Event {

    private Cell cell;

    private static final String DEAD = "dead";
    private static final double PLANT_GROWTH = 0.2;

    public SoilEvent() { }
    public SoilEvent(final int timestamp, final Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = EventPriorities.SOIL_EVENT.ordinal();
    }
    @Override
    public void takeEffect(final Simulation simulation) {
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
