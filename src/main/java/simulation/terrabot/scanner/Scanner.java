package simulation.terrabot.scanner;

import entities.Plants.Plant;
import entities.Water.Water;
import entities.animals.Animal;
import exceptions.ObjectNotFoundException;
import simulation.environmentMap.Cell;
import simulation.events.AirEvent;
import simulation.events.Event;
import simulation.events.PlantEvent;
import simulation.events.SoilEvent;
import simulation.events.animal.FeedEvent;
import simulation.events.animal.MoveEvent;
import simulation.events.water.GrowPlantEvent;
import simulation.events.water.IncreaseStatsEvent;

import java.util.Arrays;
import java.util.List;

public final class Scanner implements ScanParamsVisitor {

    private static final String SUCCESS_SCANNED_PLANT = "The scanned object is a plant.";
    private static final String SUCCESS_SCANNED_ANIMAL = "The scanned object is an animal.";
    private static final String SUCCESS_SCANNED_WATER = "The scanned object is water.";

    /**
     * Starts the visiting process.
     */
    public ScanResult scan(final ScanParams params, final Cell cell)
            throws ObjectNotFoundException {
        return params.accept(this, cell);
    }
    @Override
    public ScanResult visitPlant(final Plant plant, final int timestamp, final Cell cell) {
        List<Event> newEvents = Arrays.asList(new SoilEvent(timestamp + 1, cell),
                new PlantEvent(timestamp + 1, cell),
                new GrowPlantEvent(timestamp + 1, cell));
        return new ScanResult(SUCCESS_SCANNED_PLANT, plant.getName(), newEvents, plant);
    }
    @Override
    public ScanResult visitAnimal(final Animal animal, final int timestamp, final Cell cell) {
        List<Event> newEvents = Arrays.asList(new AirEvent(timestamp + 1, cell),
                new FeedEvent(timestamp + 1, cell),
                new MoveEvent(timestamp + 2, cell));
        return new ScanResult(SUCCESS_SCANNED_ANIMAL, animal.getName(), newEvents, animal);
    }
    @Override
    public ScanResult visitWater(final Water water, final int timestamp, final Cell cell) {
        List<Event> newEvents = Arrays.asList(new GrowPlantEvent(timestamp + 1, cell),
                new IncreaseStatsEvent(timestamp + 2, cell));
        return new ScanResult(SUCCESS_SCANNED_WATER, water.getName(), newEvents, water);
    }

}
