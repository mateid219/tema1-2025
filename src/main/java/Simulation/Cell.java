package Simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.air.Air;
import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;
import lombok.Getter;
import lombok.Setter;

import static Simulation.Simulation.MAPPER;
import Simulation.Simulation.Solution;

public final class Cell {
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private Soil soil;
    @Getter @Setter private Air air;
    @Getter @Setter private Animal animal;
    @Getter @Setter private Plant plant;
    @Getter @Setter private Water water;
    Solution solution;
    public Cell() {
        soil = null;
        air = null;
        plant = null;
        animal = null;
        water = null;
        x = -1;
        y = -1;
    }
    public Cell(final int x, final int y) {
        this();
        this.x = x;
        this.y = y;
    }
    private int entityCount() {
        int count = 0;
        if (water != null) {
            count++;
        }
        if (animal != null) {
            count++;
        }
        if (plant != null) {
            count++;
        }
        return count;
    }

    public int calculateCellQuality() {

        double score = 0.0;
        int count = 0;
        if (soil != null) {
            score += soil.possibilityToGetStuckInSoil();
            count++;
        }
        if (air != null) {
            score += air.calculateToxicity();
            count++;
        }
        if (animal != null) {
            //System.out.printf("Bear is added at cell (%d,%d) with contribution %f\n",x,y,animal.possibilityToBeAttackedByAnimal());
            score += animal.possibilityToBeAttackedByAnimal();
            count++;
        }
        if (plant != null) {
            score += plant.possibilityToGetStuckInPlants();
            count++;
        }
        double mean = Math.abs(score / count);
        return (int)Math.round(mean);
    }
    public String dbgQuality() {
        String score = String.format("Cell (%d, %d):\n",x,y);
        if (air.calculateToxicity() < 0.0) {
            score += air.calculateToxicity() +"||||" + air.buildEntityOutput() + "\n";
        }
        if (soil != null) {
            score += "S: "+soil.possibilityToGetStuckInSoil() + "\n";
        }
        if (air != null) {
            score += "Ai: "+air.calculateToxicity() + "\n";
        }
        if (animal != null) {
            score += "An: "+animal.possibilityToBeAttackedByAnimal() + "\n";
        }
        if (plant != null) {
            score += "P: "+plant.possibilityToGetStuckInPlants() + "\n";
        }
        return score + "QT:" + calculateCellQuality();
    }
    public String dbgQuality2() {
        String score = "= (";
        if (soil != null) {
            score += "Soil";
        }
        if (air != null) {
            if (!score.equals("= ("))
                score += "+";
            score += "Air";
        }
        if (animal != null) {
            if (!score.equals("= ("))
                score += "+";
            score += "Animal";
        }
        if (plant != null) {
            if (!score.equals("= ("))
                score += "+";
            score += "Plant";
        }
        score+= ") = (";
        if (soil != null) {
            score += soil.possibilityToGetStuckInSoil();
        }
        if (air != null) {
            if (!score.endsWith("("))
                score += "+";
            score += air.calculateToxicity();
        }
        if (animal != null) {
            if (!score.endsWith("("))
                score += "+";
            score += animal.possibilityToBeAttackedByAnimal();
        }
        if (plant != null) {
            if (!score.endsWith("("))
                score += "+";
            score += plant.possibilityToGetStuckInPlants();
        }
        return score + ")\n";
    }
    public String showSolution() {
        if (solution.hasWater) {
            return "the cell has water";
        }
        return "of cell order";
    }
    public ObjectNode buildEnvConditions() {
        ObjectNode entityObjectNode = MAPPER.createObjectNode();
        if (soil != null) {
            ObjectNode soilObjectNode = soil.buildEntityOutput();
            entityObjectNode.put("soil", soilObjectNode);
        }
        if (air != null) {
            ObjectNode airObjectNode = air.buildEntityOutput();
            entityObjectNode.put("air", airObjectNode);
        }
        if (animal != null) {
            ObjectNode animalObjectNode = animal.buildEntityOutput();
            entityObjectNode.put("animals", animalObjectNode);
        }
        if (plant != null) {
            ObjectNode plantObjectNode = plant.buildEntityOutput();
            entityObjectNode.put("plants", plantObjectNode);
        }
        if (water != null) {
            ObjectNode waterObjectNode = water.buildEntityOutput();
            entityObjectNode.put("water", waterObjectNode);
        }
        return entityObjectNode;
    }
    public final ObjectNode buildCellOutput() {
        ObjectNode cellObjectNode = MAPPER.createObjectNode();
        ArrayNode sectionObjectnode = MAPPER.createArrayNode();
        sectionObjectnode.add(x);
        sectionObjectnode.add(y);
        cellObjectNode.put("section", sectionObjectnode);
        cellObjectNode.put("totalNrOfObjects", entityCount());
        cellObjectNode.put("airQuality", air.interpretQuality());
        cellObjectNode.put("soilQuality", soil.interpretQuality());
        return cellObjectNode;
    }
}
