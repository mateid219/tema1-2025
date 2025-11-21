package entities.animals;

import entities.CellQualityAgent;
import entities.Entity;
import entities.Scannable;
import entities.animals.strategies.IFeedingStrategy;
import entities.animals.strategies.IMovementStrategy;
import exceptions.AnimalIsStuckException;
import exceptions.ExtraFertilizationException;
import fileio.AnimalInput;
import lombok.Getter;
import lombok.Setter;
import simulation.environmentMap.Cell;
import simulation.environmentMap.EnvironmentMap;
import simulation.terrabot.scanner.ScanParamsVisitor;
import simulation.terrabot.scanner.ScanResult;

public abstract class Animal extends Entity implements Scannable, CellQualityAgent {
    @Getter @Setter
    protected AnimalStates state;
    @Getter
    private boolean scanned;
    private final IFeedingStrategy feedingStrategy;
    protected final IMovementStrategy movementStrategy;

    private static final String OUTPUT_CATEGORY = "animals";

    static final double MAX_ATTACK = 10.0;

    public Animal(final AnimalInput animalInput, final IFeedingStrategy feedingStrategy,
                  final IMovementStrategy movementStrategy) {
        super(animalInput);
        this.feedingStrategy = feedingStrategy;
        this.movementStrategy = movementStrategy;
    }
    @Override
    public final String getPropertyName() {
        return OUTPUT_CATEGORY;
    }

    /**
     * Subclasses should override this with specific attack probability.
     * @return the attack probability of the animal
     */
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
     * Delegates move decision to the {@link #movementStrategy} class.
     */
    public final Cell move(final EnvironmentMap map, final Cell from)
            throws AnimalIsStuckException {
        return movementStrategy.move(map, from);
    }

    /**
     * Delegates feeding to the {@link #feedingStrategy} class.
     */
    public final void feed(final Cell cell) throws ExtraFertilizationException {
        feedingStrategy.feed(this, cell);
    }

    /**
     * Sets this animal's state to {@link AnimalStates#SICK}.
     */
    public final void sicken() {
        state = AnimalStates.SICK;
    }
    /**
     * Queries this animal's past feeding.
     * @return true if the animal is {@link AnimalStates#WELL_FED}
     */
    public final boolean ate() {
        return state.equals(AnimalStates.WELL_FED);
    }
    @Override
    public final double cellQualityTerm() {
        return possibilityToBeAttackedByAnimal();
    }
}
