package entities.Animals;

import Events.WaterEvent;
import entities.Entity;
import entities.Plants.Plant;
import entities.Water.Water;
import fileio.AnimalInput;
import fileio.PairInput;
import fileio.PlantInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public final class Animal extends Entity {
    @Getter @Setter private String state;
    @Getter @Setter private boolean scanned;
    @Getter @Setter private boolean full;

    private static final double INTAKE_RATE = 0.08;

    enum attackProbability {
        HERBIVORES("Herbivores", 85.0),
        CARNIVORES("Carnivores", 30.0),
        OMNIVORES("Omnivores", 60.0),
        DETRITIVORES("Detritivores", 90.0),
        PARASITES("Parasites", 10.0);

        private static final double MAX_ATTACK = 10.0;

        private final double probability;
        private final String type;
        attackProbability(String type, double probability) {
            this.probability = probability;
            this.type = type;
        }
        public double calculateProbability() {
            return (MAX_PERCENTAGE - probability) / MAX_ATTACK;
        }
    }
    public static Animal createAnimal(AnimalInput animalInput, int x, int y) {
        return new Animal(animalInput, x, y);
    }
    public Animal() {
        scanned = false;
    }
    public Animal(final AnimalInput animalInput, int x, int y) {
        this();
        name = animalInput.getName();
        type = animalInput.getType();
        mass = animalInput.getMass();
        this.x = x;
        this.y = y;
    }
    public double possibilityToBeAttackedByAnimal() {
        for (attackProbability animalType : attackProbability.values()) {
            if (type.equals(animalType.type)) {
                return animalType.calculateProbability();
            }
        }
        return 0.0;
    }
    public void eat(Animal otherAnimal) {
        mass += otherAnimal.getMass();
        System.out.print("!!!" + name + " ate animal " + otherAnimal.getName());
    }
    public void eat(Plant plant) {
        mass += plant.getMass();
        System.out.print("!!!" + name + " ate plant " + plant.getName());
    }
    public void drink(Water water) {
        double waterMass = Math.round(water.getMass() * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        double waterToDrink = Math.min(mass * INTAKE_RATE, waterMass);
        water.drain(waterToDrink);
        mass += waterToDrink;
        System.out.print("!!!" + name + " drank  " + Math.round(waterToDrink * MAX_PERCENTAGE) / MAX_PERCENTAGE + " units of water");
    }
}
