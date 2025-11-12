package Events;

import Simulation.Simulation;
import Simulation.TerraBot;

import java.awt.*;
import java.util.Queue;

public class RechargeEvent extends Event{
    private int chargeTime;

    private static final boolean BEGIN_CHARGING = true;
    private static final boolean END_CHARGING = false;

    public RechargeEvent() { }
    public RechargeEvent(int timestamp) {
        super(timestamp);
        this.chargeTime = 0;
    }
    public RechargeEvent(int timestamp, int chargeTime) {
        super(timestamp);
        this.chargeTime = chargeTime;
        priority = EVENT_PRIORITIES.ROBOT_EVENT.ordinal();
    }
    public void takeEffect(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        terraBot.charge(chargeTime);
        if (chargeTime == 0) {
            terraBot.setCharging(BEGIN_CHARGING);
            Queue<Event> eventQueue = simulation.getEventQueue();
            while (!eventQueue.isEmpty() && eventQueue.peek().getTimestamp() < timestamp + chargeTime) {
                Event event = eventQueue.poll();
                event.setTimestamp(timestamp + chargeTime);
            }
        } else {
            terraBot.setCharging(END_CHARGING);
        }
    }
}
