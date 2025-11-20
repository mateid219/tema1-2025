package entities.Plants;

import fileio.PlantInput;

public final class Moss extends Plant{

    public Moss(final PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getBaseOxygenLevel() {
        return PlantConstants.MOSSES_BASE_OXYGEN_LEVEL;
    }
    @Override
    public double getStuckProbability() {
        return PlantConstants.MOSSES_STUCK_PROBABILITY;
    }
}
