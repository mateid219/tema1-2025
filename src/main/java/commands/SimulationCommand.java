package commands;

import fileio.CommandInput;
import Simulation.Simulation;

import java.util.Arrays;
import java.util.List;

public class SimulationCommand extends Command {

    private static final String START_SIMULATION = "startSimulation";
    private static final String END_SIMULATION = "endSimulation";

    private static final String ERROR_ALREADY_STARTED = "ERROR: Simulation already started."
                                                        + " Cannot perform action";
    private static final String SUCCESS_STARTED = "Simulation has started.";
    private static final String SUCCESS_ENDED = "Simulation has ended.";

    public static boolean isSimulationCommand(final String commandName) {
        return List.of(START_SIMULATION, END_SIMULATION).contains(commandName);
    }

    public SimulationCommand() { }
    public SimulationCommand(final CommandInput commandInput) {
        super(commandInput);
    }
    public final void execute(final Simulation simulation) {
        if (START_SIMULATION.equals(super.getCommandName())) {
            if (simulation.isStarted()) {
                message = ERROR_ALREADY_STARTED;
                return;
            }
            message = SUCCESS_STARTED;
            simulation.setStarted(true);
            return;
        }
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        message = SUCCESS_ENDED;
        simulation.setEnded(true);
    }
}
