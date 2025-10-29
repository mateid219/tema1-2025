package my;

import lombok.Getter;
import lombok.Setter;

public final class TerraBot {
    @Getter @Setter private Cell cell;
    @Getter @Setter private int energyPoints;

    public TerraBot() { }
    public TerraBot(final int energyPoints) {
        this.energyPoints = energyPoints;
    }
}
