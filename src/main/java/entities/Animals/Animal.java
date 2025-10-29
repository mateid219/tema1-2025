package entities.Animals;

import entities.Entity;
import fileio.AnimalInput;
import fileio.PairInput;

import java.util.ArrayList;
import java.util.List;

public class Animal extends Entity {
    private String state;
    public Animal() { }
    public Animal(final AnimalInput animalInput) {
        name = animalInput.getName();
        mass = animalInput.getMass();
        type = animalInput.getType();
        List<PairInput> sectionsList = animalInput.getSections();
        sections = new ArrayList<>(sectionsList);
    }
}
