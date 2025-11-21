package entities.Plants;

import fileio.PlantInput;

public final class Moss extends Plant {
    private static final double STUCK_PROBABILITY = 40.0;
    private static final double BASE_OXYGEN_LEVEL = 0.8;
    public Moss(final PlantInput plantInput) {
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
