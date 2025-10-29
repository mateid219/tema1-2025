package my;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Soil.Soil;
import lombok.Getter;
import lombok.Setter;

public class Cell {
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private Soil soil;
    private static ObjectMapper MAPPER = new ObjectMapper();

    public Cell() {
        soil = null;
    }
    public Cell(final int x, final int y) {
        this();
        this.x = x;
        this.y = y;
    }
    public final ObjectNode buildCellOutput() {
        ObjectNode soilObjectNode = soil.buildEntityOutput();
        ObjectNode entityObjectNode = MAPPER.createObjectNode();
        entityObjectNode.put("soil", soilObjectNode);

        return entityObjectNode;
    }
}
