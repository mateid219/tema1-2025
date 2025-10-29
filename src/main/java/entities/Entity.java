package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;


public class Entity {
    @Getter @Setter private String name;
    @Getter @Setter private double mass;
    private static ObjectMapper MAPPER = new ObjectMapper();

    public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = MAPPER.createObjectNode();
        objectNode.put("name" , name);
        objectNode.put("mass" , mass);
        return objectNode;
    }
}
