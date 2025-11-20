package entities.animals;

import fileio.AnimalInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Parasites extends Animal {

    private static final double ATTACK_PROBABILITY = 10.0;
    public Parasites(final AnimalInput animalInput) {
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
