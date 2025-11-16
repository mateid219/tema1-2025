package entities.Animals;

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
    public double possibilityToBeAttackedByAnimal() {
        double probability = 0.0;
        for (AttackProbabilities animalType : AttackProbabilities.values()) {
            if (type.equals(animalType.type)) {
                probability = animalType.calculateProbability();
            }
        }
        return probability;
    }
    public ScanResult accept(ScanParamsVisitor visitor, int timestamp, Cell cell) {
        return visitor.visitAnimal(this, timestamp, cell);
    }
    public void eat(Entity entity) {
        mass += entity.getMass();
    }
    public void drink(Water water) {
        double waterMass = Math.round(water.getMass() * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        double waterToDrink = Math.min(mass * INTAKE_RATE, waterMass);
        water.drain(waterToDrink);
        mass += waterToDrink;
    }

}
