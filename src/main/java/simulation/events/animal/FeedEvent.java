package simulation.events.animal;

import entities.Plants.Plant;
import entities.Water.Water;
import entities.animals.Animal;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.EventPriorities;

public class FeedEvent extends AnimalEvent {
    private Animal prey = null;



    private static final double  ORGANIC_MATTER_FULL = 0.8;
    private static final double  ORGANIC_MATTER_PART = 0.5;


    public FeedEvent(int timestamp, Cell cell) {
        super(timestamp, cell);
        priority = EventPriorities.ANIMAL_FEED.ordinal();
    }
    public FeedEvent(int timestamp, Cell cell, Animal prey) {
        this(timestamp, cell);
        this.prey = prey;
    }

    @Override
    public void animalAction(final Simulation simulation, final Animal animal) {
        pushFeedEvent(simulation, cell);
        if (prey != null) {
            animal.eat(prey);
            pushFertilizeEvent(simulation, ORGANIC_MATTER_PART);
            return;
        }
        Water water = cell.getWater();
        Plant plant = cell.getPlant();
        if ((water == null || !water.isScanned())
                && (plant == null || !plant.isScanned())) {
            animal.starve();
            return;
        }
        if (water == null || !water.isScanned()) {
            animal.eat(plant);
            cell.removePlant();
            pushFertilizeEvent(simulation, ORGANIC_MATTER_PART);
            return;
        }
        if (plant == null || !plant.isScanned()) {
            animal.drink(water);
            if (water.getMass() == 0.0) {
                cell.removeWater();
            }
            simulation.addEvent(new FertilizeEvent(timestamp, cell, ORGANIC_MATTER_PART));
            return;
        }

        if (water.getScanTime() == plant.getScanTime()) {
            animal.eat(plant);
            cell.removePlant();
            animal.drink(water);
            if (water.getMass() == 0.0) {
                cell.removeWater();
            }
            pushFertilizeEvent(simulation, ORGANIC_MATTER_FULL);
            return;
        }
        if (water.getScanTime() < plant.getScanTime()) {
            animal.drink(water);
            if (water.getMass() == 0.0) {
                cell.removeWater();
            }
        } else {
            animal.eat(plant);
            cell.removePlant();
        }
        pushFertilizeEvent(simulation, ORGANIC_MATTER_PART);
    }
}
