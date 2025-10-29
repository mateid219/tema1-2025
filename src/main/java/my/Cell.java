package my;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Air.Air;
import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import lombok.Getter;
import lombok.Setter;

public class Cell {
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private Soil soil;
    @Getter @Setter private Air air;
    @Getter @Setter private Animal animal;
    @Getter @Setter private Plant plant;

    private static ObjectMapper MAPPER = new ObjectMapper();

    public Cell() {
        soil = null;
        air = null;
        plant = null;
    }
    public Cell(final int x, final int y) {
        this();
        this.x = x;
        this.y = y;
    }
    public final ObjectNode buildCellOutput() {
        ObjectNode soilObjectNode = soil.buildEntityOutput();
        ObjectNode airObjectNode = air.buildEntityOutput();
        ObjectNode animalObjectNode = animal.buildEntityOutput();
        ObjectNode plantObjectNode = plant.buildEntityOutput();

        ObjectNode entityObjectNode = MAPPER.createObjectNode();
        entityObjectNode.put("soil", soilObjectNode);
        entityObjectNode.put("air", airObjectNode);
        entityObjectNode.put("animals", animalObjectNode);
        entityObjectNode.put("plants", plantObjectNode);

        return entityObjectNode;
    }
}
