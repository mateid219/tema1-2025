package simulation.terraBot;

import exceptions.BatteryException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Battery {
    @Getter private int energyPoints;
    @Getter private boolean charging;
    public Battery(final int energyPoints) {
        charging = false;
        this.energyPoints = energyPoints;
    }

    /**
     * Begins the charging of the battery.
     */
    public void beginCharging() {
        charging = true;
    }
    /**
     * Finishes the charging of the battery.
     */
    public void finishCharging(final int chargeTime) {
        energyPoints += chargeTime;
        charging = false;
    }

    /**
     * Checks if there is enough battery for a request.
     * @param points the requested battery usage
     * @throws BatteryException if there is not enough battery left.
     */
    public void processUseRequest(final int points)
            throws BatteryException {
        if (points > this.energyPoints) {
            throw new BatteryException();
        }
    }

    /**
     * Drains the battery by specified amount.
     * @param points the battery points to be drained.
     */
    public void drain(final int points) {
        this.energyPoints -= points;
    }
}
