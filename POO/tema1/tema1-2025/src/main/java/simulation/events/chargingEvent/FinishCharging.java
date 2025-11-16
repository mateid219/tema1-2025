package simulation.events.chargingEvent;

import simulation.events.Event;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.events.EventPriorities;
import simulation.terrabot.TerraBot;

@NoArgsConstructor
public final class FinishCharging extends Event {
    private int chargeTime;
    public FinishCharging(final int timestamp, final int chargeTime) {
        super(timestamp);
        this.chargeTime = chargeTime;
        priority = EventPriorities.ROBOT_EVENT.ordinal();
    }

    @Override
    public void takeEffect(final Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        terraBot.finishCharging(chargeTime);
    }
}
