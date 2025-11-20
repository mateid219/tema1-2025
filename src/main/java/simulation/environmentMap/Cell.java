package simulation.environmentMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;
import entities.air.Air;
import entities.animals.Animal;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public final class Cell {
    @Getter private final int x;
    @Getter private final int y;
    @Getter @Setter private Soil soil;
    @Getter @Setter private Air air;
    @Getter @Setter private Animal animal;
    @Getter @Setter private Plant plant;
    @Getter @Setter private Water water;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    public void removePlant() {
        plant = null;
    }
    public void removeWater() {
        water = null;
    }
    public void addAnimal(Animal animal) {
        this.animal = animal;
    }
    public void removeAnimal() {
        animal = null;
    }
    private int entityCount() {
        int count = 0;
        for (Entity entity : Arrays.asList(water, animal, plant)) {
            if (entity != null) {
                count++;
            }
        }
        return count;
    }
    /**
     * Builds the objectNode required for printEnvConditions
     * @return output for printEnvConditions command
     */
    public ObjectNode buildEnvConditions() {
        ObjectNode entityObjectNode = MAPPER.createObjectNode();
        for (Entity entity : Arrays.asList(soil, air, animal, plant, water)) {
            if (entity != null) {
                entityObjectNode.set(entity.getPropertyName(), entity.buildEntityOutput());
            }
        }
        return entityObjectNode;
    }
    /**
     * Builds this cell's objectNode required for printMap
     * @return part of the output for printMap command
     */
    public ObjectNode buildCellOutput() {
        ObjectNode cellObjectNode = MAPPER.createObjectNode();
        ArrayNode sectionObjectNode = MAPPER.createArrayNode();
        sectionObjectNode.add(x);
        sectionObjectNode.add(y);
        cellObjectNode.set("section", sectionObjectNode);
        cellObjectNode.put("totalNrOfObjects", entityCount());
        cellObjectNode.put("airQuality", air.interpretQuality());
        cellObjectNode.put("soilQuality", soil.interpretQuality());
        return cellObjectNode;
    }
}