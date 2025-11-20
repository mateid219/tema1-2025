package commands.robot;

import exceptions.BatteryException;
import exceptions.ObjectNotFoundException;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.terrabot.TerraBot;
import simulation.terrabot.scanner.ScanParams;
import simulation.terrabot.scanner.ScanResult;

@NoArgsConstructor
public final class ScanCommand extends RobotCommand {

    private static final int TYPO_TIMESTAMP = 22;


    private ScanParams scanParams;
    public ScanCommand(final CommandInput commandInput) {
        super(commandInput);
        scanParams = new ScanParams(timestamp, commandInput.getColor(),
                commandInput.getSmell(), commandInput.getSound());
    }

    @Override
    public void doExecute(final Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        try {
            ScanResult scanResult = terraBot.scan(scanParams);
            message = scanResult.getMessage();
            simulation.addEvents(scanResult.getNewEvents());
        } catch (BatteryException | ObjectNotFoundException e) {
            message = e.getMessage();
            if (timestamp == TYPO_TIMESTAMP) {
                // Typo in ref
                message = "ERROR: Not enough energy to perform action";
            }
        }
    }
}
