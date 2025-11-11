package commands;

import Events.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;
import Simulation.Simulation;

import java.util.Queue;

public abstract class Command {
    @Getter @Setter protected String commandName;
    @Getter @Setter protected String message;
    @Getter @Setter protected int timestamp;
    @Getter @Setter protected ObjectNode commandObjectOutput;
    @Getter @Setter protected ArrayNode commandArrayOutput;

    protected static final String ERROR_NOT_STARTED = "ERROR: Simulation not started. Cannot perform action";
    private static ObjectMapper MAPPER = new ObjectMapper();

    private static final String TYPE_SIMULATION = "Simulation";
    private static final String TYPE_DEBUG = "Debug";
    private static final String TYPE_ROBOT = "Robot";
    private static final String TYPE_ENVIRONMENT = "Environment";

    private static String commandType(final String command) {
        if (SimulationCommand.isSimulationCommand(command)) {
            return TYPE_SIMULATION;
        }
        if (DebugCommand.isDebugCommand(command)) {
            return TYPE_DEBUG;
        }
        if (RobotCommand.isRobotCommand(command)) {
            return TYPE_ROBOT;
        }
        if (EnvironmentCommand.isEnvironmentCommand(command)) {
            return TYPE_ENVIRONMENT;
        }
        return null;
    }
    public static Command createCommand(CommandInput commandInput) {
        String commandName = commandInput.getCommand();
        return switch (commandType(commandName)) {
            case TYPE_SIMULATION -> new SimulationCommand(commandInput);
            case TYPE_DEBUG -> new DebugCommand(commandInput);
            case TYPE_ROBOT -> new RobotCommand(commandInput);
            case TYPE_ENVIRONMENT -> new EnvironmentCommand(commandInput);
            case null, default -> null;
        };
    }
    public Command() {
        commandArrayOutput = null;
        commandObjectOutput = null;
        message = null;
    }
    public Command(final CommandInput commandInput) {
        this();
        commandName = commandInput.getCommand();
        timestamp = commandInput.getTimestamp();
    }
    public void executeInSync(Simulation simulation) {
        Queue<Event> eventQueue = simulation.getEventQueue();
        while (!eventQueue.isEmpty() && eventQueue.peek().getTimestamp() <= timestamp) {
            Event event = eventQueue.poll();
            event.takeEffect(simulation);
        }
        execute(simulation);
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
