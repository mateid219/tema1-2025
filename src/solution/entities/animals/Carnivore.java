package entities.animals;

import entities.animals.strategies.IFeedingStrategy;
import entities.animals.strategies.IMovementStrategy;
import fileio.AnimalInput;

public final class Carnivore extends Animal {
    private static final double ATTACK_PROBABILITY = 30.0;
    public Carnivore(final AnimalInput animalInput, final IFeedingStrategy feedingStrategy,
                     final IMovementStrategy movementStrategy) {
        super(animalInput, feedingStrategy, movementStrategy);
    }
    @Override
    public double getAttackProbability() {
        return ATTACK_PROBABILITY;
    }

}
