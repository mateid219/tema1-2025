package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.PairInput;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Entity {
    public static final double MAX_PERCENTAGE = 100.0;
   // public static final double EPS = 1e-4;

    @Getter @Setter protected String name;
    @Getter @Setter protected String type;
    @Getter @Setter protected double mass;
    @Getter @Setter protected int x;
    @Getter @Setter protected int y;
    protected static ObjectMapper MAPPER = new ObjectMapper();

    public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = MAPPER.createObjectNode();
        objectNode.put("type", type);
        objectNode.put("name", name);

        mass = Math.round(mass * MAX_PERCENTAGE) / MAX_PERCENTAGE;

        objectNode.put("mass", mass);
        return objectNode;
    }
}
