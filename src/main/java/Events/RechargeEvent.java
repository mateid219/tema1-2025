package Events;

import Simulation.Simulation;
import Simulation.TerraBot;

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
    }
    public void takeEffect(Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        terraBot.charge(chargeTime);
        if (chargeTime == 0) {
            terraBot.setCharging(BEGIN_CHARGING);
        } else {
            terraBot.setCharging(END_CHARGING);
        }
    }
}
