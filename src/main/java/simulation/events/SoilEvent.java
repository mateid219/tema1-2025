package simulation.events;

import entities.Plants.Plant;
import simulation.Simulation;
import simulation.environmentMap.Cell;

public final class SoilEvent extends Event {

    private Cell cell;

    private static final double PLANT_GROWTH = 0.2;

    public SoilEvent() { }
    public SoilEvent(final int timestamp, final Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = EventPriorities.SOIL_EVENT.ordinal();
    }
    @Override
    public void takeEffect(final Simulation simulation) {
        Plant plant = cell.getPlant();
        if (plant == null) {
            return;
        }
        plant.grow(PLANT_GROWTH);
        if (plant.hasDied()) {
            cell.setPlant(null);
        } else {
            simulation.addEvent(new SoilEvent(timestamp + 1, cell));
        }
    }
}
