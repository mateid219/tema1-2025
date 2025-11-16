package commands.simulation;

import commands.CommandConstants;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class EndSimulation extends SimulationCommand {
    public EndSimulation(final CommandInput commandInput) {
        super(commandInput);
    }

    @Override
    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = CommandConstants.ERROR_NOT_STARTED;
            return;
        }
        message = CommandConstants.SUCCESS_ENDED;
        simulation.setEnded(true);
    }
}
