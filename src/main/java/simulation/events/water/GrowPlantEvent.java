package simulation.events.water;

import entities.Plants.Plant;
import entities.Water.Water;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.EventPriorities;

public final class GrowPlantEvent extends WaterEvent {
    private static final double PLANT_GROWTH = 0.2;
    private static final int GROW_PLANT_DELAY = 1;
    public GrowPlantEvent(final int timestamp, final Cell cell) {
        super(timestamp, cell);
        priority = EventPriorities.WATER_EVENT_GROW_PLANT.ordinal();
    }

    @Override
    public void waterAction(Simulation simulation, Water water) {
        Plant plant = cell.getPlant();
        if (plant == null || !plant.isScanned()) {
            return;
        }
        plant.grow(PLANT_GROWTH);
        if (plant.hasDied()) {
            cell.setPlant(null);
        } else {
            simulation.addEvent(new GrowPlantEvent(timestamp + GROW_PLANT_DELAY, cell));
        }
    }
}
