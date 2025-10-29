package commands;

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
            super.setMessage("ERROR: Simulation not started. Cannot perform action");
        }
        final String printEnvConditions = DBG_COMMANDS[0];
        final String commandName = super.getCommandName();
        if (printEnvConditions.equals(commandName)) {
            ObjectNode objectNode = simulation.printEnvConditions();
            super.setCommandOutput(objectNode);
        }
    }
}
