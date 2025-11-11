package Events;

import Simulation.Cell;
import Simulation.Simulation;
import entities.air.Air;
import entities.Plants.Plant;

import java.util.Queue;

public class PlantEvent extends Event {
    Cell cell;

    public PlantEvent() { }
    public PlantEvent(int timestamp, Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = PLANT_EVENT_PRIORITY;
    }

    @Override
    public void takeEffect(Simulation simulation) {
        Plant plant = cell.getPlant();
        if (plant == null) {
            return;
        }
        Air air = cell.getAir();
        double oxygenGenerated = plant.oxygenGenerated();
        air.addOxygen(oxygenGenerated);
        Queue<Event> eventQueue = simulation.getEventQueue();
        eventQueue.add(new PlantEvent(timestamp + 1, cell));
    }
}
