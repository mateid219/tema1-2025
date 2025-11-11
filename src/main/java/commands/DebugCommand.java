package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import Simulation.Simulation;
import Simulation.TerraBot;

import java.util.Arrays;

public final class DebugCommand extends Command {

    static final String[] DBG_COMMANDS = {
            "printEnvConditions", "printMap",
            "printKnowledgeBase", "getEnergyStatus"
    };
    public static boolean isDebugCommand(final String commandName) {
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
        TerraBot terraBot = simulation.getTerraBot();
        if (terraBot.isCharging()) {
            message = "ERROR: Robot still charging. Cannot perform action";
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
        final String getEnergyStatus = DBG_COMMANDS[3];
        if (getEnergyStatus.equals(commandName)) {
            message = "TerraBot has " + terraBot.getEnergyPoints() + " energy points left.";
        }
    }
}
