package commands.simulation;

import exceptions.AlreadyStartedException;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class StartSimulation extends SimulationCommand {
    public static final String SUCCESS_STARTED = "Simulation has started.";
    public StartSimulation(final CommandInput commandInput) {
        super(commandInput);
    }

    @Override
    public void execute(final Simulation simulation) throws AlreadyStartedException {
        if (simulation.isStarted()) {
            message = ERROR_ALREADY_STARTED;
            return;
        }
        message = SUCCESS_STARTED;
        simulation.setStarted(true);
    }
}
