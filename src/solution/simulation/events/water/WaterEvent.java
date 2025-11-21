package simulation.events.water;

import entities.water.Water;
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
    public final void takeEffect(final Simulation simulation) {
        Water water = cell.getWater();
        if (water == null || !water.isScanned()) {
            return;
        }
        waterAction(simulation, water);
    }
    abstract void waterAction(Simulation simulation, Water water);
}
