package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.EntityInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class Entity {
    public static final double MAX_PERCENTAGE = 100.0;

    @Getter @Setter protected String name;
    @Getter @Setter protected String type;
    @Getter @Setter protected double mass;
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    public Entity(final EntityInput entityInput) {
        name = entityInput.getName();
        type = entityInput.getType();
        mass = entityInput.getMass();
    }

    /**
     * Subclasses that register as cell properties should override this.
     * @return the property name associated to the class
     */
    public abstract String getPropertyName();
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
