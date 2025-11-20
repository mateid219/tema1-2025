package entities.Plants;

import fileio.PlantInput;

public final class Fern extends Plant {

    public Fern(final PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getBaseOxygenLevel() {
        return PlantConstants.FERNS_BASE_OXYGEN_LEVEL;
    }
    @Override
    public double getStuckProbability() {
        return PlantConstants.FERNS_STUCK_PROBABILITY;
    }
}
