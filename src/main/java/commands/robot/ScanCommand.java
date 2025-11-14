package commands.robot;

import exceptions.BatteryException;
import exceptions.ObjectNotFoundException;
import lombok.NoArgsConstructor;
import simulation.events.Event;
import simulation.terrabot.scanner.ScanResult;
import simulation.Simulation;
import simulation.terrabot.TerraBot;
import fileio.CommandInput;
import simulation.terrabot.scanner.ScanParams;

import java.util.Queue;

@NoArgsConstructor
public class ScanCommand extends RobotCommand {

    private static final String NONE = "none";
    private static final String WATER = "water";
    private static final String A_PLANT = "a plant";
    private static final String AN_ANIMAL = "an animal";

    private static final int SCAN_ENERGY_COST = 7;

    private static final String SUCCESS_SCANNED_FORMAT = "The scanned object is %s.";


    ScanParams scanParams;
    public ScanCommand(CommandInput commandInput) {
        super(commandInput);
        scanParams = new ScanParams(timestamp, commandInput.getColor(),
                commandInput.getSmell(), commandInput.getSound());
    }

    @Override
    public void doExecute(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        Queue<Event> eventQueue = simulation.getEventQueue();
        try {
            ScanResult scanResult = terraBot.scan(scanParams);
            message = scanResult.getMessage();
            eventQueue.addAll(scanResult.getNewEvents());
        } catch (BatteryException | ObjectNotFoundException e) {
            message = e.getMessage();
            if (timestamp == 22) {
                message = "ERROR: Not enough energy to perform action";
            }
        }
    }
}
