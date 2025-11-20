package entities.animals;

import fileio.AnimalInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Herbivores extends Animal {

    private static final double ATTACK_PROBABILITY = 85.0;
    public Herbivores(final AnimalInput animalInput) {
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
