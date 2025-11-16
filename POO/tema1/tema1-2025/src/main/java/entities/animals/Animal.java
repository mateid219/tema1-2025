package entities.animals;

import entities.Entity;
import entities.Scannable;
import entities.Water.Water;
import fileio.AnimalInput;
import lombok.Getter;
import lombok.Setter;
import simulation.Cell;
import simulation.terrabot.scanner.ScanParamsVisitor;
import simulation.terrabot.scanner.ScanResult;

public final class Animal extends Entity implements Scannable {
    @Getter @Setter private String state;
    @Getter @Setter private boolean scanned;
    @Getter @Setter private boolean full;

    private static final double INTAKE_RATE = 0.08;

    public Animal() {

    }
    public Animal(final AnimalInput animalInput) {
        name = animalInput.getName();
        type = animalInput.getType();
        mass = animalInput.getMass();
        scanned = false;
    }

    /**
     * Uses AttackProbabilities enum to search for specific animal probability.
     * @return animal-specific probability
     */
    public double possibilityToBeAttackedByAnimal() {
        double probability = 0.0;
        for (AttackProbabilities animalType : AttackProbabilities.values()) {
            if (type.equals(animalType.type)) {
                probability = animalType.calculateProbability();
            }
        }
        return probability;
    }
    @Override
    public ScanResult accept(final ScanParamsVisitor visitor,
                             final int timestamp, final Cell cell) {
        return visitor.visitAnimal(this, timestamp, cell);
    }

    /**
     * Eats a specified entity, gaining its mass.
     * @param entity entity to be eaten
     */
    public void eat(final Entity entity) {
        mass += entity.getMass();
    }

    /**
     * Calculates the
     * @param water the water to drink from
     */
    public void drink(final Water water) {
        double waterMass = Math.round(water.getMass() * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        double waterToDrink = Math.min(mass * INTAKE_RATE, waterMass);
        water.drain(waterToDrink);
        mass += waterToDrink;
    }

}
