package entities.animals;

import entities.CellQualityAgent;
import entities.Entity;
import entities.Scannable;
import entities.Water.Water;
import fileio.AnimalInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simulation.environmentMap.Cell;
import simulation.terrabot.scanner.ScanParamsVisitor;
import simulation.terrabot.scanner.ScanResult;

@NoArgsConstructor
public abstract class Animal extends Entity implements Scannable, CellQualityAgent {
    @Getter private AnimalStates state;
    @Getter private boolean scanned;

    private static final double MAX_ATTACK = 10.0;
    private static final double INTAKE_RATE = 0.08;
    private static final String OUTPUT_CATEGORY = "animals";

    @Override
    public String getPropertyName() {
        return OUTPUT_CATEGORY;
    }

    public Animal(final AnimalInput animalInput) {
        super(animalInput);
        scanned = false;
    }
    public abstract double getAttackProbability();
    /**
     * Calculates attack probability.
     * @return animal-specific probability
     */

    public double possibilityToBeAttackedByAnimal() {
        return (MAX_PERCENTAGE - getAttackProbability()) / MAX_ATTACK;
    }
    @Override
    public final ScanResult accept(final ScanParamsVisitor visitor,
                             final int timestamp, final Cell cell) {
        scanned = true;
        return visitor.visitAnimal(this, timestamp, cell);
    }

    /**
     * Eats a specified entity, gaining its mass.
     * @param entity entity to be eaten
     */
    public void eat(final Entity entity) {
        mass += entity.getMass();
        state = AnimalStates.WELL_FED;
    }

    public void starve() {
        state = AnimalStates.HUNGRY;
    }

    public void sicken() {
        state = AnimalStates.SICK;
    }

    public abstract boolean isPredator();
    public boolean ate() {
        return state.equals(AnimalStates.WELL_FED);
    }


    /**
     * Calculates the
     * @param water the water to drink from
     */
    public void drink(final Water water) {
        mass += water.beDrankBy(mass, INTAKE_RATE);
        state = AnimalStates.WELL_FED;
    }

    @Override
    public double cellQualityTerm() {
        return possibilityToBeAttackedByAnimal();
    }
}
