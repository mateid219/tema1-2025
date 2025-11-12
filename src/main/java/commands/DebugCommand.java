package commands;

import fileio.CommandInput;
import Simulation.Simulation;
import Simulation.TerraBot;

import java.util.List;

public final class DebugCommand extends Command {

    private static final String PRINT_ENV_CONDITIONS = "printEnvConditions";
    private static final String PRINT_MAP = "printMap";
    private static final String PRINT_KNOWLEDGE_BASE = "printKnowledgeBase";
    private static final String GET_ENERGY_STATUS = "getEnergyStatus";

    public static boolean isDebugCommand(final String commandName) {
        return List.of(PRINT_ENV_CONDITIONS, PRINT_MAP,
                PRINT_KNOWLEDGE_BASE, GET_ENERGY_STATUS).contains(commandName);
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
        if (PRINT_ENV_CONDITIONS.equals(commandName)) {
            commandObjectOutput = simulation.printEnvConditions();
            return;
        }
        if (PRINT_MAP.equals(commandName)) {
            commandArrayOutput = simulation.printMap();
            return;
        }
        if (GET_ENERGY_STATUS.equals(commandName)) {
            message = "TerraBot has " + terraBot.getEnergyPoints() + " energy points left.";
            return;
        }
        if (PRINT_KNOWLEDGE_BASE.equals(commandName)) {
            commandArrayOutput = simulation.getTerraBot().printKnowledgeBase();
        }
    }
}
