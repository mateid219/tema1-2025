package commands.robot;

import commands.Command;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.terrabot.TerraBot;


@NoArgsConstructor
public abstract class RobotCommand extends Command {

    protected static final String ERROR_BATTERY =
            "ERROR: Not enough battery left. Cannot perform action";


    public RobotCommand(final CommandInput commandInput) {
        super(commandInput);
    }

    /**
     *  Subclasses must override this with specialized execution.
     */
    public abstract void doExecute(Simulation simulation);

    /**
     * Generic error handling for robot commands. Execution is delegated to specialized subclasses.
     */
    public final void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        TerraBot terraBot = simulation.getTerraBot();
        if (terraBot.isCharging()) {
            message = ERROR_CHARGING;
            return;
        }
        doExecute(simulation);
    }
}
