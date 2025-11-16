package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;


public class Entity {
    public static final double MAX_PERCENTAGE = 100.0;

    @Getter @Setter protected String name;
    @Getter @Setter protected String type;
    @Getter @Setter protected double mass;
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Builds the entity output representation.
     *
     * <p>When overriding this method, subclasses should:
     * <ul>
     *   <li>Call super.buildEntityOutput() to include base functionality</li>
     *   <li>Handle any additional properties specific to the subclass</li>
     *   <li>Ensure the output format remains consistent</li>
     * </ul>
     *
     * @return the formatted entity objectNode
     */
    public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = MAPPER.createObjectNode();
        objectNode.put("type", type);
        objectNode.put("name", name);

        mass = Math.round(mass * MAX_PERCENTAGE) / MAX_PERCENTAGE;

        objectNode.put("mass", mass);
        return objectNode;
    }
}
