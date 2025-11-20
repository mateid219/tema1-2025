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
        return switch (plantInput.getType()) {
            case PlantConstants.ANGIOSPERMS -> new FloweringPlant(plantInput);
            case PlantConstants.GYMNOSPERMS -> new GymnospermsPlant(plantInput);
            case PlantConstants.FERNS -> new Fern(plantInput);
            case PlantConstants.MOSSES -> new Moss(plantInput);
            case PlantConstants.ALGAE -> new Algae(plantInput);
            default -> null;
        };
    }
}
