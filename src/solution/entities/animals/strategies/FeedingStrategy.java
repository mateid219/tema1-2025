package entities.animals.strategies;

import entities.plants.Plant;
import entities.water.Water;
import entities.animals.Animal;
import entities.animals.AnimalStates;
import exceptions.ExtraFertilizationException;
import simulation.environmentMap.Cell;

public final class FeedingStrategy implements IFeedingStrategy {
    private static final double INTAKE_RATE = 0.08;

    private static final AnimalFoodSelector FOOD_SELECTOR = new AnimalFoodSelector();
    private void starve(final Animal animal) {
        animal.setState(AnimalStates.HUNGRY);
    }

    private void eat(final Animal animal, final Plant plant) {
        animal.setMass(animal.getMass() + plant.beEaten());
        animal.setState(AnimalStates.WELL_FED);
    }
    private void drink(final Animal animal, final Water water) {
        double maxWaterIntake = INTAKE_RATE * animal.getMass();
        animal.setMass(animal.getMass() + water.beDrank(maxWaterIntake));
        animal.setState(AnimalStates.WELL_FED);
    }
    @Override
    public void feed(final Animal animal, final Cell cell)
            throws ExtraFertilizationException {
        Water water = cell.getWater();
        if (water != null && !water.isScanned()) {
            water = null;
        }
        Plant plant = cell.getPlant();
        if (plant != null && !plant.isScanned()) {
            plant = null;
        }
        if (water == null && plant == null) {
            starve(animal);
            return;
        }
        if (FOOD_SELECTOR.compare(water, plant) >= 0) {
            drink(animal, water);
        }
        if (FOOD_SELECTOR.compare(plant, water) >= 0) {
            eat(animal, plant);
        }
        if (FOOD_SELECTOR.compare(plant, water) == 0) {
            throw new ExtraFertilizationException();
        }
    }
}
