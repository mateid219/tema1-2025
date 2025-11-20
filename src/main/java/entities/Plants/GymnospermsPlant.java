package entities.Plants;

import fileio.PlantInput;

public final class GymnospermsPlant extends Plant {

    public GymnospermsPlant(final PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getBaseOxygenLevel() {
        return PlantConstants.GYMNOSPERMS_BASE_OXYGEN_LEVEL;
    }
    @Override
    public double getStuckProbability() {
        return PlantConstants.GYMNOSPERMS_STUCK_PROBABILITY;
    }
}
