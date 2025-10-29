package commands;

import fileio.CommandInput;
import my.Simulation;

import java.util.Arrays;

public class SimulationCommand extends Command {

    static final String[] SIM_COMMANDS = {
            "startSimulation", "endSimulation"
    };
    public static boolean isSimulationCommand(final String commandName) {
        return Arrays.asList(SIM_COMMANDS).contains(commandName);
    }

    public SimulationCommand() { }
    public SimulationCommand(final CommandInput commandInput) {
        super(commandInput);
    }
    public final void execute(final Simulation simulation) {
        final String startSimulation = SIM_COMMANDS[0];
        if (startSimulation.equals(super.getCommandName())) {
            if (simulation.isStarted()) {
                super.setMessage("ERROR: Simulation already started. Cannot perform action");
                return;
            }
            super.setMessage("Simulation has started.");
            simulation.setStarted(true);
            return;
        }
        if (!simulation.isStarted()) {
            super.setMessage("ERROR: Simulation not started. Cannot perform action");
            return;
        }
        super.setMessage("Simulation has ended.");
        simulation.setEnded(true);
    }
}
