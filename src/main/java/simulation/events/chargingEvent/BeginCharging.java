package simulation.events.chargingEvent;

import simulation.events.Event;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.events.EventPriorities;
import simulation.terrabot.TerraBot;

@NoArgsConstructor
public final class BeginCharging extends Event {
    public BeginCharging(final int timestamp) {
        super(timestamp);
        priority = EventPriorities.ROBOT_EVENT.ordinal();
    }
    @Override
    public void takeEffect(final Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        terraBot.beginCharging();
    }
}
