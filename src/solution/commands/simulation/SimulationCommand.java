package commands.simulation;

import commands.Command;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

/**
 * Starts/stops a given simulation.
 */
@NoArgsConstructor
public abstract class SimulationCommand extends Command {
    /**
     * Only extracts useful fields from commandInput
     * @param commandInput the command which is implemented.
     */
    public SimulationCommand(final CommandInput commandInput) {
        super(commandInput);
    }

    /**
     * Executes a simulation command.
     * Always sets a valid message in superclass.
     * @param simulation the simulation which is either started or ended.
     *                   If simulation was initialized with the empty constructor,
     *                   message is set to MODIFY ME
     */
    public abstract void execute(Simulation simulation);
}
