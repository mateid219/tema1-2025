package simulation.terrabot.scanner;

import entities.Plants.Plant;
import entities.Water.Water;
import entities.animals.Animal;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.PlantEvent;
import simulation.events.SoilEvent;
import simulation.events.water.GrowPlantEvent;

public record EventScheduler(Simulation simulation) {
    public void schedule(final Plant plant,
                         final Cell cell, int timestamp) {
        simulation.addEvent(new SoilEvent(timestamp + 1, cell));
        simulation.addEvent(new PlantEvent(timestamp + 1, cell));
        simulation.addEvent(new GrowPlantEvent(timestamp + 1, cell));
    }

    public void schedule(final Animal plant,
                         final Cell cell, int timestamp) {
        simulation.addEvent(new SoilEvent(timestamp + 1, cell));
        simulation.addEvent(new PlantEvent(timestamp + 1, cell));
        simulation.addEvent(new GrowPlantEvent(timestamp + 1, cell));
    }

    public void schedule(final Water plant,
                         final Cell cell, int timestamp) {
        simulation.addEvent(new SoilEvent(timestamp + 1, cell));
        simulation.addEvent(new PlantEvent(timestamp + 1, cell));
        simulation.addEvent(new GrowPlantEvent(timestamp + 1, cell));
    }
}
