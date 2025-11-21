package entities.plants;

import fileio.PlantInput;

public final class Flowering extends Plant {
    private static final double STUCK_PROBABILITY = 90.0;
    private static final double BASE_OXYGEN_LEVEL = 6.0;
    public Flowering(final PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getBaseOxygenLevel() {
        return BASE_OXYGEN_LEVEL;
    }
    @Override
    public double getStuckProbability() {
        return STUCK_PROBABILITY;
    }
}
