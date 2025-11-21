package commands.debug;

import commands.Command;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

/**
 * Prints information about the state of the simulation.
 */
@NoArgsConstructor
public abstract class DebugCommand extends Command {

    public DebugCommand(final CommandInput commandInput) {
        super(commandInput);
    }
    /**
     * Executes a debug command.
     * @param simulation the simulation which is either started or ended.
     */
    @Override
    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        if (simulation.getTerraBot().isCharging()) {
            message = ERROR_CHARGING;
            return;
        }
        doExecute(simulation);
    }

    /**
     * Should be implemented by subclasses.
     * Executes the given debug command.
     */
    public abstract void doExecute(Simulation simulation);
}
