package Simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Animals.Animal;
import entities.Entity;
import entities.Plants.Plant;
import entities.Water.Water;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.Map;

import static Simulation.Simulation.MAPPER;

public final class TerraBot {
    @Getter @Setter private Cell cell;
    @Getter @Setter private int energyPoints;
    @Getter @Setter private boolean charging;
    @Getter ArrayList<Plant> plantInventory;
    @Getter ArrayList<Water> waterInventory;
    @Getter ArrayList<Animal> animalInventory;
    @Getter Map<String, ArrayList<Entity>> inventory;
    @Getter Map<String, ArrayList<String>> database;

    public TerraBot() {
        charging = false;
        plantInventory = new ArrayList<>();
        waterInventory = new ArrayList<>();
        animalInventory = new ArrayList<>();
        inventory = new HashMap<>();
        database = new LinkedHashMap<>();
    }
    public TerraBot(final int energyPoints) {
        this();
        this.energyPoints = energyPoints;
    }
    public void charge(int points) {
        energyPoints += points;
    }
    public ArrayNode printKnowledgeBase() {
        ArrayNode output = MAPPER.createArrayNode();
        System.out.println("DATABASE :::::\n" + database);
        for (Map.Entry<String, ArrayList<String>> entry : database.entrySet()) {
            ObjectNode entryNode = MAPPER.createObjectNode();
            entryNode.put("topic", entry.getKey());
            ArrayNode facts = MAPPER.createArrayNode();
            for (String fact : entry.getValue()) {
                facts.add(fact);
            }
            entryNode.put("facts", facts);
            output.add(entryNode);
        }
        return output;
    }
}
