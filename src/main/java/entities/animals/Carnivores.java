package entities.animals;

import fileio.AnimalInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Carnivores extends Animal {
    private static final double ATTACK_PROBABILITY = 30.0;
    public Carnivores(final AnimalInput animalInput) {
        super(animalInput);
    }
    @Override
    public double getAttackProbability() {
        return ATTACK_PROBABILITY;
    }

    @Override
    public boolean isPredator() {
        return true;
    }

}
