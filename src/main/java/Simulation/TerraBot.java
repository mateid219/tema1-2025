package Simulation;

import entities.Animals.Animal;
import entities.Entity;
import entities.Plants.Plant;
import entities.Water.Water;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class TerraBot {
    @Getter @Setter private Cell cell;
    @Getter @Setter private int energyPoints;
    @Getter @Setter private boolean charging;
    @Getter ArrayList<Plant> plantInventory;
    @Getter ArrayList<Water> waterInventory;
    @Getter ArrayList<Animal> animalInventory;
    @Getter Map<Entity, ArrayList<String>> database;

    public TerraBot() {
        charging = false;
        plantInventory = new ArrayList<>();
        waterInventory = new ArrayList<>();
        animalInventory = new ArrayList<>();
        database = new HashMap<>();
    }
    public TerraBot(final int energyPoints) {
        this();
        this.energyPoints = energyPoints;
    }
    public void charge(int points) {
        energyPoints += points;
    }
}
