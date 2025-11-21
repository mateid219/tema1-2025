package commands.simulation;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class EndSimulation extends SimulationCommand {

    public static final String SUCCESS_ENDED = "Simulation has ended.";

    public EndSimulation(final CommandInput commandInput) {
        super(commandInput);
    }

    @Override
    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        message = SUCCESS_ENDED;
        simulation.setEnded(true);
    }
}
