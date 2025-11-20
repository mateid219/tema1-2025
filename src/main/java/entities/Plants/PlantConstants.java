package entities.Plants;

import lombok.Getter;

public final class PlantConstants {
    private PlantConstants() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    static final String OUTPUT_CATEGORY = "plants";
    static final String ANGIOSPERMS = "FloweringPlants";
    static final String GYMNOSPERMS = "GymnospermsPlants";
    static final String FERNS = "Ferns";
    static final String MOSSES = "Mosses";
    static final String ALGAE = "Algae";

    static final double MAX_GROWTH = 1.0;
    static final double ANGIOSPERMS_STUCK_PROBABILITY = 90.0;
    static final double ANGIOSPERMS_BASE_OXYGEN_LEVEL = 6.0;
    static final double GYMNOSPERMS_STUCK_PROBABILITY = 60.0;
    static final double GYMNOSPERMS_BASE_OXYGEN_LEVEL = 0.0;
    static final double FERNS_STUCK_PROBABILITY = 30.0;
    static final double FERNS_BASE_OXYGEN_LEVEL = 0.0;
    static final double MOSSES_STUCK_PROBABILITY = 40.0;
    static final double MOSSES_BASE_OXYGEN_LEVEL = 0.8;
    static final double ALGAE_STUCK_PROBABILITY = 20.0;
    static final double ALGAE_BASE_OXYGEN_LEVEL = 0.5;
    enum LifeStages {
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
}
