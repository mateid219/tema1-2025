package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;
import my.Simulation;

public abstract class Command {
    @Getter @Setter protected String commandName;
    @Getter @Setter protected String message;
    @Getter @Setter protected int timestamp;
    @Getter @Setter protected ObjectNode commandObjectOutput;
    @Getter @Setter protected ArrayNode commandArrayOutput;

    private static ObjectMapper MAPPER = new ObjectMapper();

    public Command() {
        commandArrayOutput = null;
        commandObjectOutput = null;
    }
    public Command(final CommandInput commandInput) {
        commandName = commandInput.getCommand();
        timestamp = commandInput.getTimestamp();
    }
    public abstract void execute(Simulation simulation);
    public ObjectNode buildCommandOutput() {
        ObjectNode objectNode = MAPPER.createObjectNode();
        objectNode.put("command", commandName);
        if (message != null) {
            objectNode.put("message", message);
        }
        if (commandObjectOutput != null) {
            objectNode.put("output", commandObjectOutput);
        }
        if (commandArrayOutput != null) {
            objectNode.put("output", commandArrayOutput);
        }
        objectNode.put("timestamp", timestamp);
        return objectNode;
    }
}
