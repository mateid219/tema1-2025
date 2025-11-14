package simulation.events;

import simulation.Cell;
import simulation.Simulation;
import simulation.terrabot.TerraBot;
import entities.air.Air;
import entities.Plants.Plant;

import java.util.Queue;

public class PlantEvent extends Event {
    Cell cell;

    public PlantEvent() { }
    public PlantEvent(int timestamp, Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = EVENT_PRIORITIES.PLANT_EVENT.ordinal();
    }

    @Override
    public void takeEffect(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        Queue<Event> eventQueue = simulation.getEventQueue();

        Plant plant = cell.getPlant();
        if (plant == null) {
            return;
        }
        Air air = cell.getAir();
        double oxygenGenerated = plant.oxygenGenerated();
        air.increaseOxygen(oxygenGenerated);
        eventQueue.add(new PlantEvent(timestamp + 1, cell));
    }
}
