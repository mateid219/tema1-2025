package entities.animals;

import fileio.AnimalInput;

public final class AnimalFactory {
    private AnimalFactory() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    /**
     * Creates an animal entity.
     * @return the newly created animal
     */
    public static Animal createAnimal(final AnimalInput animalInput) {
        return new Animal(animalInput);
    }
}
