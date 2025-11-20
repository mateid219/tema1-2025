package entities.animals;

import fileio.AnimalInput;

public class Omnivores extends Animal {

    private static final double ATTACK_PROBABILITY = 60.0;
    public Omnivores(final AnimalInput animalInput) {
        super(animalInput);
    }
    @Override
    public double getAttackProbability() {
        return ATTACK_PROBABILITY;
    }

    @Override
    public boolean isPredator() {
        return false;
    }

}
