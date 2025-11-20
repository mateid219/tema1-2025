package entities.animals;

import fileio.AnimalInput;

public final class AnimalFactory {
    private AnimalFactory() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    private static final String HERBIVORES = "Herbivores";
    private static final String CARNIVORES = "Carnivores";
    private static final String OMNIVORES = "Omnivores";
    private static final String DETRITIVORES = "Detritivores";
    private static final String PARASITES = "Parasites";
    /**
     * Creates an animal entity.
     * @return the newly created animal
     */
    public static Animal createAnimal(final AnimalInput animalInput) {
        return switch (animalInput.getType()) {
            case HERBIVORES -> new Herbivores(animalInput);
            case CARNIVORES -> new Carnivores(animalInput);
            case OMNIVORES -> new Omnivores(animalInput);
            case DETRITIVORES -> new Detritivores(animalInput);
            case PARASITES -> new Parasites(animalInput);
            default -> null;
        };
    }
}
