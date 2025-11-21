package entities.plants;

import fileio.PlantInput;

public final class PlantFactory {
    private PlantFactory() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    private static final String ANGIOSPERMS = "FloweringPlants";
    private static final String GYMNOSPERMS = "GymnospermsPlants";
    private static final String FERNS = "Ferns";
    private static final String MOSSES = "Mosses";
    private static final String ALGAE = "Algae";
    /**
     * Creates a plant entity.
     * @return the newly created plant
     */
    public static Plant createPlant(final PlantInput plantInput) {
        return switch (plantInput.getType()) {
            case ANGIOSPERMS -> new Flowering(plantInput);
            case GYMNOSPERMS -> new Gymnosperm(plantInput);
            case FERNS -> new Fern(plantInput);
            case MOSSES -> new Moss(plantInput);
            case ALGAE -> new Algae(plantInput);
            default -> null;
        };
    }
}
