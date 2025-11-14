package simulation.terrabot.scanner;

import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Water.Water;
import exceptions.ObjectNotFoundException;
import simulation.Cell;
import simulation.events.*;

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

    public ScanResult scan(ScanParams params, Cell cell, int timestamp)
            throws ObjectNotFoundException {
        return params.accept(this, cell, timestamp);
    }
    @Override
    public ScanResult visitPlant(Plant plant, int timestamp, Cell cell) {
        plant.setScanned(true);
        plant.setScanTime(timestamp);
        List<Event> newEvents = Arrays.asList(new SoilEvent(timestamp + 1, cell),
                new PlantEvent(timestamp + 1, cell),
                new WaterEvent(timestamp + 1, cell, GROW_PLANT));
        return new ScanResult(SUCCESS_SCANNED_PLANT, plant.getName(), newEvents, plant);
    }
    public ScanResult visitAnimal(Animal animal, int timestamp, Cell cell) {
        animal.setScanned(true);
        List<Event> newEvents = Arrays.asList(new AirEvent(timestamp + 1, cell),
                new AnimalEvent(timestamp + 1, cell, FEED),
                new AnimalEvent(timestamp + 2, cell, MOVE));
        return new ScanResult(SUCCESS_SCANNED_ANIMAL, animal.getName(), newEvents, animal);
    }
    public ScanResult visitWater(Water water, int timestamp, Cell cell) {
        water.setScanned(true);
        water.setScanTime(timestamp);
        List<Event> newEvents = Arrays.asList(new WaterEvent(timestamp + 1, cell, GROW_PLANT),
                new WaterEvent(timestamp + 2, cell, INCREASE_STATS));
        return new ScanResult(SUCCESS_SCANNED_WATER, water.getName(), newEvents, water);
    }

}
