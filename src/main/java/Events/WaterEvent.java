package Events;

import Simulation.Simulation;
import Simulation.TerraBot;
import Simulation.Cell;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;
import entities.air.Air;

import java.util.Queue;

public class WaterEvent extends Event {

    Cell cell;
    int type;

    private static final String DEAD = "dead";
    private static final double PLANT_GROWTH = 0.2;
    private static final double AIR_HUMIDITY_INCREASE = 0.1;
    private static final double SOIL_WATER_RETENTION_INCREASE = 0.1;

    public static final int INCREASE_STATS = 1;
    public static final int GROW_PLANT = 2;

    public WaterEvent() { }
    public WaterEvent(int timestamp, Cell cell, int type) {
        super(timestamp);
        this.cell = cell;
        this.type = type;
        if (type == INCREASE_STATS)
            priority = EVENT_PRIORITIES.WATER_EVENT_INCREASE_STATS.ordinal();
        else
            priority = EVENT_PRIORITIES.WATER_EVENT_GROW_PLANT.ordinal();
    }
    public void takeEffect(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        Queue<Event> eventQueue = simulation.getEventQueue();
        Water water = cell.getWater();
        if (water == null || ! water.isScanned()) {
            return;
        }
        switch (type) {
            case INCREASE_STATS:
                Air air = cell.getAir();
                Soil soil = cell.getSoil();
                soil.increaseWaterRetention(SOIL_WATER_RETENTION_INCREASE);
                air.increaseHumidity(AIR_HUMIDITY_INCREASE);
                eventQueue.add(new WaterEvent(timestamp + 2, cell, INCREASE_STATS));
                return;
            case GROW_PLANT:
                Plant plant = cell.getPlant();
                if (plant == null || ! plant.isScanned()) {
                    return;
                }
                plant.grow(PLANT_GROWTH);
                if (DEAD.equals(plant.getMaturity())) {
                    cell.setPlant(null);
                } else {
                    eventQueue.add(new WaterEvent(timestamp + 1, cell, GROW_PLANT));
                }
            default:
                break;
        }
    }
}
