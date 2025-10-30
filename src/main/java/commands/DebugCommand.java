package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import my.Simulation;

import java.util.Arrays;

public final class DebugCommand extends Command {

    static final String[] DBG_COMMANDS = {
            "printEnvConditions", "printMap",
            "printKnowledgeBase", "getEnergyStatus"
    };
    public static boolean isSimulationCommand(final String commandName) {
        return Arrays.asList(DBG_COMMANDS).contains(commandName);
    }

    public DebugCommand() { }
    public DebugCommand(final CommandInput commandInput) {
        super(commandInput);
    }
    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = "ERROR: Simulation not started. Cannot perform action";
            return;
        }

        final String printEnvConditions = DBG_COMMANDS[0];
        if (printEnvConditions.equals(commandName)) {
            ObjectNode objectNode = simulation.printEnvConditions();
            commandObjectOutput = objectNode;
        }
        final String printMap = DBG_COMMANDS[1];
        if (printMap.equals(commandName)) {
            ArrayNode objectNode = simulation.printMap();
            commandArrayOutput = objectNode;
        }
    }
}
