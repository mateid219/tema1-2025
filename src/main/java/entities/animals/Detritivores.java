package entities.animals;

import entities.animals.strategies.IFeedingStrategy;
import entities.animals.strategies.IMovementStrategy;
import fileio.AnimalInput;

public final class Detritivores extends Animal {
    private static final double ATTACK_PROBABILITY = 90.0;
    public Detritivores(final AnimalInput animalInput, final IFeedingStrategy feedingStrategy,
                      final IMovementStrategy movementStrategy) {
        super(animalInput, feedingStrategy, movementStrategy);
    }
    @Override
    public double getAttackProbability() {
        return ATTACK_PROBABILITY;
    }

}
