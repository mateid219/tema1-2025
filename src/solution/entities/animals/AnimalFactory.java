package entities.animals;

import entities.animals.strategies.AnimalMovementStrategy;
import entities.animals.strategies.FeedingStrategy;
import entities.animals.strategies.IMovementStrategy;
import entities.animals.strategies.PredatorMovementStrategy;
import fileio.AnimalInput;

public final class AnimalFactory {
    private AnimalFactory() {
        throw new AssertionError("Utility classes should not be instantiated.");
    }
    private static final String HERBIVORES = "Herbivores";
    private static final String CARNIVORES = "Carnivores";
    private static final String OMNIVORES = "Omnivores";
    private static final String DETRITIVORES = "Detritivores";
    private static final String PARASITES = "Parasites";
    private static final FeedingStrategy FEEDING_STRATEGY = new FeedingStrategy();
    private static final IMovementStrategy DEFAULT_MOVEMENT_STRATEGY = new AnimalMovementStrategy();
    private static final IMovementStrategy PREDATOR_MOVEMENT_STRATEGY =
            new PredatorMovementStrategy();
    /**
     * Creates an animal entity.
     *
     * @return the newly created animal
     */
    public static Animal createAnimal(final AnimalInput animalInput) {
        return switch (animalInput.getType()) {
            case HERBIVORES ->
                    new Herbivore(animalInput, FEEDING_STRATEGY, DEFAULT_MOVEMENT_STRATEGY);
            case CARNIVORES ->
                    new Carnivore(animalInput, FEEDING_STRATEGY, PREDATOR_MOVEMENT_STRATEGY);
            case OMNIVORES ->
                    new Omnivore(animalInput, FEEDING_STRATEGY, DEFAULT_MOVEMENT_STRATEGY);
            case DETRITIVORES ->
                    new Detritivore(animalInput, FEEDING_STRATEGY, DEFAULT_MOVEMENT_STRATEGY);
            case PARASITES ->
                    new Parasite(animalInput, FEEDING_STRATEGY, PREDATOR_MOVEMENT_STRATEGY);
            default -> null;
        };
    }
}
