package commands.robot;

import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.terraBot.TerraBot;
import simulation.environmentMap.EnvironmentMap;
import exceptions.BatteryException;
import fileio.CommandInput;

@NoArgsConstructor
public final class MoveCommand extends RobotCommand {
    private static final String SUCCESS_MOVED_FORMAT =
            "The robot has successfully moved to position (%d, %d).";
    public MoveCommand(final CommandInput commandInput) {
        super(commandInput);
    }

    /**
     * Attempts to move terraBot. Always sets message to a non-null String.
     * On failure, message will contain the error.
     */
    public void doExecute(final Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        EnvironmentMap map = simulation.getEnvironmentMap();
        try {
            terraBot.move(map);
            message = String.format(SUCCESS_MOVED_FORMAT,
                    terraBot.getCell().getX(), terraBot.getCell().getY());
        } catch (BatteryException e) {
            message = e.getMessage();
        }
    }
}
