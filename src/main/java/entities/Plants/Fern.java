package entities.Plants;

import fileio.PlantInput;

public final class Fern extends Plant {
    private static final double STUCK_PROBABILITY = 30.0;
    private static final double BASE_OXYGEN_LEVEL = 0.0;
    public Fern(final PlantInput plantInput) {
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
