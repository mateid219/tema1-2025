package commands.simulation;

import commands.CommandConstants;
import exceptions.AlreadyStartedException;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class StartSimulation extends SimulationCommand {
    public StartSimulation(final CommandInput commandInput) {
        super(commandInput);
    }

    @Override
    public void execute(final Simulation simulation) throws AlreadyStartedException {
        if (simulation.isStarted()) {
            message = CommandConstants.ERROR_ALREADY_STARTED;
            return;
        }
        message = CommandConstants.SUCCESS_STARTED;
        simulation.setStarted(true);
    }
}
