package entities.Animals;

import fileio.AnimalInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class AnimalArrayList {
    @Getter @Setter private ArrayList<Animal> animalArrayList;
    public AnimalArrayList() { }
    public AnimalArrayList(final ArrayList<AnimalInput> animalInputArrayList) {
        animalArrayList = new ArrayList<>();
        for (AnimalInput animalInput : animalInputArrayList) {
            Animal animal = new Animal(animalInput);
            animalArrayList.add(animal);
        }
    }
}
