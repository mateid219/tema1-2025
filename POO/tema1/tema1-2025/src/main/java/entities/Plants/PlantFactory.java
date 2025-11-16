package entities.Plants;

import fileio.PlantInput;

public final class PlantFactory {
    private PlantFactory() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    /**
     * Creates a plant entity.
     * @return the newly created plant
     */
    public static Plant createPlant(final PlantInput plantInput) {
        return new Plant(plantInput);
    }
}
