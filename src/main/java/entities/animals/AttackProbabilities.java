package entities.Animals;

import static entities.Entity.MAX_PERCENTAGE;

public enum AttackProbabilities {
    HERBIVORES("Herbivores", 85.0),
    CARNIVORES("Carnivores", 30.0),
    OMNIVORES("Omnivores", 60.0),
    DETRITIVORES("Detritivores", 90.0),
    PARASITES("Parasites", 10.0);

    private static final double MAX_ATTACK = 10.0;

    public final double probability;
    public final String type;
    AttackProbabilities(String type, double probability) {
        this.probability = probability;
        this.type = type;
    }
    public double calculateProbability() {
        return (MAX_PERCENTAGE - probability) / MAX_ATTACK;
    }
}
