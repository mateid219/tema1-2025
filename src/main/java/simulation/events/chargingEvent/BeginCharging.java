package simulation.events.chargingEvent;

import simulation.events.Event;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.terrabot.TerraBot;

@NoArgsConstructor
public final class BeginCharging extends Event{
    public BeginCharging(int timestamp) {
        super(timestamp);
        priority = EVENT_PRIORITIES.ROBOT_EVENT.ordinal();
    }
    @Override
    public void takeEffect(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        terraBot.beginCharging();
    }
}
