package commands.debug;

import commands.Command;
import commands.CommandConstants;
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
     *                   If simulation was initialized with the empty constructor,
     *                   message is set to {@link CommandConstants#SUCCESS_STARTED}
     */
    @Override
    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = CommandConstants.ERROR_NOT_STARTED;
            return;
        }
        if (simulation.getTerraBot().isCharging()) {
            message = CommandConstants.ERROR_CHARGING;
            return;
        }
        doExecute(simulation);
    }
    public abstract void doExecute(final Simulation simulation);
}
