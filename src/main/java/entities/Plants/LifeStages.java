package entities.Plants;

import lombok.Getter;

public enum LifeStages {
    YOUNG(0.2),
    MATURE(0.7),
    OLD(0.4),
    DEAD(0.0);
    @Getter
    private final double oxygenRate;
    LifeStages(final double oxygenRate) {
        this.oxygenRate = oxygenRate;
    }
}
