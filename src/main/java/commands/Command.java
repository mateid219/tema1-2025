package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;
import my.Simulation;

public abstract class Command {
    @Getter @Setter private String commandName;
    @Getter @Setter private String message;
    @Getter @Setter private int timestamp;
    @Getter @Setter private ObjectNode commandOutput;

    private static ObjectMapper MAPPER = new ObjectMapper();

    public Command() { }
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
        if (commandOutput != null) {
            objectNode.put("output", commandOutput);
        }
        objectNode.put("timestamp", timestamp);
        return objectNode;
    }
}
