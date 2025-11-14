package commands.robot;

import simulation.events.Event;
import simulation.events.chargingEvent.BeginCharging;
import simulation.events.chargingEvent.FinishCharging;
import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

import java.util.Queue;

@NoArgsConstructor
public class RechargeCommand extends RobotCommand {

    private static final String SUCCESS_CHARGING = "Robot battery is charging.";

    int timeToCharge;

    public RechargeCommand(CommandInput commandInput) {
        super(commandInput);
        timeToCharge = commandInput.getTimeToCharge();
    }

    @Override
    public void doExecute(Simulation simulation) {
        Queue<Event> eventQueue = simulation.getEventQueue();
        new BeginCharging(timestamp).takeEffect(simulation);
        eventQueue.add(new FinishCharging(timestamp + timeToCharge, timeToCharge));
        message = SUCCESS_CHARGING;
    }
}
