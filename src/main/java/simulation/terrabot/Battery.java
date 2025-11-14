package simulation.terrabot;

import exceptions.BatteryException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Battery {
    @Getter private int energyPoints;
    @Getter private boolean charging;
    public Battery(int energyPoints) {
        charging = false;
        this.energyPoints = energyPoints;
    }
    public void beginCharging() {
        charging = true;
    }
    public void finishCharging(int chargeTime) {
        energyPoints += chargeTime;
        charging = false;
    }
    public void processUseRequest(int energyPoints)
            throws BatteryException {
        if (energyPoints > this.energyPoints) {
            throw new BatteryException();
        }
    }
    public void drain(int energyPoints) {
        this.energyPoints -= energyPoints;
    }
}
