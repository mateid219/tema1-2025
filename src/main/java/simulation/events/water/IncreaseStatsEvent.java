package simulation.events.water;

import entities.Soil.Soil;
import entities.Water.Water;
import entities.air.Air;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.EventPriorities;

public final class IncreaseStatsEvent extends WaterEvent {
    private static final double AIR_HUMIDITY_INCREASE = 0.1;
    private static final double SOIL_WATER_RETENTION_INCREASE = 0.1;
    private static final int INCREASE_STATS_DELAY = 2;

    public IncreaseStatsEvent(final int timestamp, final Cell cell) {
        super(timestamp, cell);
        priority = EventPriorities.WATER_EVENT_INCREASE_STATS.ordinal();
    }

    @Override
    public void waterAction(Simulation simulation, Water water) {
        Air air = cell.getAir();
        Soil soil = cell.getSoil();
        soil.increaseWaterRetention(SOIL_WATER_RETENTION_INCREASE);
        air.increaseHumidity(AIR_HUMIDITY_INCREASE);
        simulation.addEvent(new IncreaseStatsEvent(timestamp + INCREASE_STATS_DELAY, cell));
    }
}
