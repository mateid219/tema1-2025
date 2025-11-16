package entities.Animals;

import fileio.AnimalInput;

public class AnimalFactory {
    public static Animal createAnimal(AnimalInput animalInput) {
        return new Animal(animalInput);
    }
}
