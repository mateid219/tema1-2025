package simulation.events.water;

import entities.Water.Water;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.Event;

public abstract class WaterEvent extends Event {

    protected Cell cell;

    public WaterEvent(final int timestamp, final Cell cell) {
        super(timestamp);
        this.cell = cell;
    }
    @Override
    public void takeEffect(final Simulation simulation) {
        Water water = cell.getWater();
        if (water == null || !water.isScanned()) {
            return;
        }
        waterAction(simulation, water);
    }
    public abstract void waterAction(final Simulation simulation, final Water water);
}
