package my;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Air.Air;
import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.stream.Stream;

public class Cell {
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private Soil soil;
    @Getter @Setter private Air air;
    @Getter @Setter private Animal animal;
    @Getter @Setter private Plant plant;
    @Getter @Setter private Water water;

    protected static ObjectMapper MAPPER = new ObjectMapper();

    public Cell() {
        soil = null;
        air = null;
        plant = null;
        animal = null;
        water = null;
    }
    public Cell(final int x, final int y) {
        this();
        this.x = x;
        this.y = y;
    }
    private int entityCount() {
        return (int) Stream.of(water, animal, plant)
                .filter(Objects::nonNull)
                .count();
    }
    public final ObjectNode buildEnvConditions() {
        ObjectNode soilObjectNode = soil.buildEntityOutput();
        ObjectNode airObjectNode = air.buildEntityOutput();
        ObjectNode animalObjectNode = animal.buildEntityOutput();
        ObjectNode plantObjectNode = plant.buildEntityOutput();
        ObjectNode waterObjectNode = water.buildEntityOutput();

        ObjectNode entityObjectNode = MAPPER.createObjectNode();
        entityObjectNode.put("soil", soilObjectNode);
        entityObjectNode.put("air", airObjectNode);
        entityObjectNode.put("animals", animalObjectNode);
        entityObjectNode.put("plants", plantObjectNode);
        entityObjectNode.put("water", waterObjectNode);

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
