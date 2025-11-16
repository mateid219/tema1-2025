package simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;
import entities.air.Air;
import lombok.Getter;
import lombok.Setter;

public final class Cell {
    @Getter private int x;
    @Getter private int y;
    @Getter @Setter private Soil soil;
    @Getter @Setter private Air air;
    @Getter @Setter private Animal animal;
    @Getter @Setter private Plant plant;
    @Getter @Setter private Water water;
    private static final ObjectMapper MAPPER = new ObjectMapper();
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

    /**
     * @return Quality of this cell
     */
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
            score += animal.possibilityToBeAttackedByAnimal();
            count++;
        }
        if (plant != null) {
            score += plant.possibilityToGetStuckInPlants();
            count++;
        }
        double mean = Math.abs(score / count);
        return (int) Math.round(mean);
    }

    /**
     * Builds the objectNode required for printEnvConditions
     * @return output for printEnvConditions command
     */
    public ObjectNode buildEnvConditions() {
        ObjectNode entityObjectNode = MAPPER.createObjectNode();
        if (soil != null) {
            ObjectNode soilObjectNode = soil.buildEntityOutput();
            entityObjectNode.set("soil", soilObjectNode);
        }
        if (air != null) {
            ObjectNode airObjectNode = air.buildEntityOutput();
            entityObjectNode.set("air", airObjectNode);
        }
        if (animal != null) {
            ObjectNode animalObjectNode = animal.buildEntityOutput();
            entityObjectNode.set("animals", animalObjectNode);
        }
        if (plant != null) {
            ObjectNode plantObjectNode = plant.buildEntityOutput();
            entityObjectNode.set("plants", plantObjectNode);
        }
        if (water != null) {
            ObjectNode waterObjectNode = water.buildEntityOutput();
            entityObjectNode.set("water", waterObjectNode);
        }
        return entityObjectNode;
    }
    /**
     * Builds this cell's objectNode required for printMap
     * @return part of the output for printMap command
     */
    public ObjectNode buildCellOutput() {
        ObjectNode cellObjectNode = MAPPER.createObjectNode();
        ArrayNode sectionObjectnode = MAPPER.createArrayNode();
        sectionObjectnode.add(x);
        sectionObjectnode.add(y);
        cellObjectNode.set("section", sectionObjectnode);
        cellObjectNode.put("totalNrOfObjects", entityCount());
        cellObjectNode.put("airQuality", air.interpretQuality());
        cellObjectNode.put("soilQuality", soil.interpretQuality());
        return cellObjectNode;
    }
}
