package entities.animals;

import fileio.AnimalInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Detritivores extends Animal {
    private static final double ATTACK_PROBABILITY = 90.0;
    public Detritivores(final AnimalInput animalInput) {
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
