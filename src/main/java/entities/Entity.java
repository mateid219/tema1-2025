package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.PairInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


public class Entity {
    @Getter @Setter protected String name;
    @Getter @Setter protected String type;
    @Getter @Setter protected double mass;
    @Getter @Setter protected ArrayList<PairInput> sections;
    protected static ObjectMapper MAPPER = new ObjectMapper();

    public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = MAPPER.createObjectNode();
        objectNode.put("type", type);
        objectNode.put("name", name);
        objectNode.put("mass", mass);
        return objectNode;
    }
}
