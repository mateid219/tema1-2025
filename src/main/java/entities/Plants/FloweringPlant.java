package entities.Plants;

import fileio.PlantInput;

public final class FloweringPlant extends Plant {

    public FloweringPlant(final PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getBaseOxygenLevel() {
        return PlantConstants.ANGIOSPERMS_BASE_OXYGEN_LEVEL;
    }
    @Override
    public double getStuckProbability() {
        return PlantConstants.ANGIOSPERMS_STUCK_PROBABILITY;
    }
}
