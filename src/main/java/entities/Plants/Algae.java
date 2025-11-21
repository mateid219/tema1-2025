package entities.Plants;

import fileio.PlantInput;

public final class Algae extends Plant {
    private static final double STUCK_PROBABILITY = 20.0;
    private static final double BASE_OXYGEN_LEVEL = 0.5;
    public Algae(final PlantInput plantInput) {
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
