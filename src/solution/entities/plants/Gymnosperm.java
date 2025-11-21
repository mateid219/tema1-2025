package entities.plants;

import fileio.PlantInput;

public final class Gymnosperm extends Plant {
    private static final double STUCK_PROBABILITY = 60.0;
    private static final double BASE_OXYGEN_LEVEL = 0.0;
    public Gymnosperm(final PlantInput plantInput) {
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
