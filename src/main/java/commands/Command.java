package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;
import simulation.Simulation;

/**
 * Abstract base class for all commands in the simulation.
 *
 * <p>This abstract class defines the common interface and behavior
 * for various command types. Concrete commands should extend this class
 * and implement the {@link #execute} method
 *
 */
public abstract class Command {
    @Getter protected String commandName;
    @Getter @Setter protected String message;
    @Getter protected int timestamp;
    @Getter protected ObjectNode commandObjectOutput;
    @Getter protected ArrayNode commandArrayOutput;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    public Command() { }
    public Command(final CommandInput commandInput) {
        this();
        commandName = commandInput.getCommand();
        timestamp = commandInput.getTimestamp();
    }

    /**
     * Always sets at least one of the following fields to a valid, non-null value:
     * <ul>
     *     <li>{@link #message} for commands that show success/error messages</li>
     *     <li>{@link #commandObjectOutput} for commands that output an
     *     {@link com.fasterxml.jackson.databind.node.ObjectNode;#ObjectNode}</li>
     *     <li>{@link #commandArrayOutput} for commands that output an
     *     {@link com.fasterxml.jackson.databind.node.ArrayNode;#ArrayNode}</li>
     * </ul>
     */
    public abstract void execute(Simulation simulation);

    /**
     *  Synchronises each command execution with the automated environment interactions.
     */
    public final void executeInSync(final Simulation simulation) {
        simulation.update(timestamp);
        execute(simulation);
    }

    /**
     * Creates and builds the required ObjectNode with the command execution details
     * @return objectNode
     */
    public final ObjectNode buildCommandOutput() {
        if (commandObjectOutput != null && commandArrayOutput != null) {
            throw new AssertionError("Command has multiple outputs.");
        }
        ObjectNode objectNode = MAPPER.createObjectNode();
        objectNode.put("command", commandName);
        if (message != null) {
            objectNode.put("message", message);
        }
        if (commandObjectOutput != null) {
            objectNode.set("output", commandObjectOutput);
        }
        if (commandArrayOutput != null) {
            objectNode.set("output", commandArrayOutput);
        }
        objectNode.put("timestamp", timestamp);
        return objectNode;
    }
}
