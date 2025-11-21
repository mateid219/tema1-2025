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

    public static final Scanner INSTANCE = new Scanner();
    private Scanner() { }
    private static final String SUCCESS_SCANNED_PLANT = "The scanned object is a plant.";
    private static final String SUCCESS_SCANNED_ANIMAL = "The scanned object is an animal.";
    private static final String SUCCESS_SCANNED_WATER = "The scanned object is water.";

    private static final int PLANT_EVENT_DELAY = 1;
    private static final int GROW_EVENT_DELAY = 1;
    private static final int SOIL_EVENT_DELAY = 1;

    private static final int AIR_EVENT_DELAY = 1;
    private static final int FEED_EVENT_DELAY = 1;
    private static final int MOVE_EVENT_DELAY = 2;
    private static final int INCREASE_STATS_EVENT_DELAY = 2;

    /**
     * Starts the visiting process.
     */
    public ScanResult scan(final ScanParams params, final Cell cell)
            throws ObjectNotFoundException {
        return params.accept(this, cell);
    }
    @Override
    public ScanResult visitPlant(final Plant plant, final int timestamp, final Cell cell) {
        List<Event> newEvents = Arrays.asList(new SoilEvent(timestamp + SOIL_EVENT_DELAY, cell),
                new PlantEvent(timestamp + PLANT_EVENT_DELAY, cell),
                new GrowPlantEvent(timestamp + GROW_EVENT_DELAY, cell));
        return new ScanResult(SUCCESS_SCANNED_PLANT, plant.getName(), newEvents, plant);
    }
    @Override
    public ScanResult visitAnimal(final Animal animal, final int timestamp, final Cell cell) {
        List<Event> newEvents = Arrays.asList(new AirEvent(timestamp + AIR_EVENT_DELAY, cell),
                new FeedEvent(timestamp + FEED_EVENT_DELAY, cell),
                new MoveEvent(timestamp + MOVE_EVENT_DELAY, cell));
        return new ScanResult(SUCCESS_SCANNED_ANIMAL, animal.getName(), newEvents, animal);
    }
    @Override
    public ScanResult visitWater(final Water water, final int timestamp, final Cell cell) {
        List<Event> newEvents = Arrays.asList(
                new IncreaseStatsEvent(timestamp + INCREASE_STATS_EVENT_DELAY, cell),
                new GrowPlantEvent(timestamp + GROW_EVENT_DELAY, cell)
        );
        return new ScanResult(SUCCESS_SCANNED_WATER, water.getName(), newEvents, water);
    }

}
