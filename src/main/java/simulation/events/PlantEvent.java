package simulation.events;

import entities.Plants.Plant;
import simulation.Simulation;
import simulation.environmentMap.Cell;

public final class PlantEvent extends Event {
    private final Cell cell;

    public PlantEvent(final int timestamp, final Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = EventPriorities.PLANT_EVENT.ordinal();
    }

    @Override
    public void takeEffect(final Simulation simulation) {
        Plant plant = cell.getPlant();
        if (plant == null) {
            return;
        }
        plant.interact(cell);
        simulation.addEvent(new PlantEvent(timestamp + 1, cell));
    }
}
