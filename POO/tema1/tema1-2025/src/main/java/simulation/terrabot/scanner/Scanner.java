package simulation.terrabot.scanner;

import entities.animals.Animal;
import entities.Plants.Plant;
import entities.Water.Water;
import exceptions.ObjectNotFoundException;
import simulation.Cell;
import simulation.events.AirEvent;
import simulation.events.AnimalEvent;
import simulation.events.Event;
import simulation.events.PlantEvent;
import simulation.events.SoilEvent;
import simulation.events.WaterEvent;

import java.util.Arrays;
import java.util.List;

import static simulation.events.AnimalEvent.FEED;
import static simulation.events.AnimalEvent.MOVE;
import static simulation.events.WaterEvent.GROW_PLANT;
import static simulation.events.WaterEvent.INCREASE_STATS;

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
        plant.setScanned(true);
        plant.setScanTime(timestamp);
        List<Event> newEvents = Arrays.asList(new SoilEvent(timestamp + 1, cell),
                new PlantEvent(timestamp + 1, cell),
                new WaterEvent(timestamp + 1, cell, GROW_PLANT));
        return new ScanResult(SUCCESS_SCANNED_PLANT, plant.getName(), newEvents, plant);
    }
    @Override
    public ScanResult visitAnimal(final Animal animal, final int timestamp, final Cell cell) {
        animal.setScanned(true);
        List<Event> newEvents = Arrays.asList(new AirEvent(timestamp + 1, cell),
                new AnimalEvent(timestamp + 1, cell, FEED),
                new AnimalEvent(timestamp + 2, cell, MOVE));
        return new ScanResult(SUCCESS_SCANNED_ANIMAL, animal.getName(), newEvents, animal);
    }
    @Override
    public ScanResult visitWater(final Water water, final int timestamp, final Cell cell) {
        water.setScanned(true);
        water.setScanTime(timestamp);
        List<Event> newEvents = Arrays.asList(new WaterEvent(timestamp + 1, cell, GROW_PLANT),
                new WaterEvent(timestamp + 2, cell, INCREASE_STATS));
        return new ScanResult(SUCCESS_SCANNED_WATER, water.getName(), newEvents, water);
    }

}
