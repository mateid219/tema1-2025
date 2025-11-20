package commands.robot;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.events.chargingEvent.BeginCharging;
import simulation.events.chargingEvent.FinishCharging;

@NoArgsConstructor
public final class RechargeCommand extends RobotCommand {

    private static final String SUCCESS_CHARGING = "Robot battery is charging.";

    private int timeToCharge;

    public RechargeCommand(final CommandInput commandInput) {
        super(commandInput);
        timeToCharge = commandInput.getTimeToCharge();
    }

    @Override
    public void doExecute(final Simulation simulation) {
        new BeginCharging(timestamp).takeEffect(simulation);
        simulation.addEvent(new FinishCharging(timestamp + timeToCharge, timeToCharge));
        message = SUCCESS_CHARGING;
    }
}
