package simulation.events.chargingEvent;

import simulation.events.Event;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.terrabot.TerraBot;

@NoArgsConstructor
public class FinishCharging extends Event {
    int chargeTime;
    public FinishCharging(int timestamp, int chargeTime) {
        super(timestamp);
        this.chargeTime = chargeTime;
        priority = EVENT_PRIORITIES.ROBOT_EVENT.ordinal();
    }

    @Override
    public void takeEffect(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        terraBot.finishCharging(chargeTime);
    }
}
