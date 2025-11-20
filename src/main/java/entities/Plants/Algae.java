package entities.Plants;

import fileio.PlantInput;

public final class Algae extends Plant {

    public Algae(final PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getBaseOxygenLevel() {
        return PlantConstants.ALGAE_BASE_OXYGEN_LEVEL;
    }
    @Override
    public double getStuckProbability() {
        return PlantConstants.ALGAE_STUCK_PROBABILITY;
    }
}
