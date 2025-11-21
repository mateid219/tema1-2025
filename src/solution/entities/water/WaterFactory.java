package entities.water;

import fileio.WaterInput;

public final class WaterFactory {
    private WaterFactory() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }

    /**
     * Creates a water entity.
     * @return the newly created water
     */
    public static Water createWater(final WaterInput waterInput) {
        return new Water(waterInput);
    }
}
