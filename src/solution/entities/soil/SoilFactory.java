package entities.soil;

import exceptions.UnknownSoilException;
import fileio.SoilInput;

public final class SoilFactory {
    private SoilFactory() throws AssertionError {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    private static final String FOREST = "ForestSoil";
    private static final String DESERT = "DesertSoil";
    private static final String SWAMP = "SwampSoil";
    private static final String TUNDRA = "TundraSoil";
    private static final String GRASSLAND = "GrasslandSoil";
    /**
     * Creates a soil entity.
     * @return the newly created soil
     */
    public static Soil createSoil(final SoilInput soilInput) {
        return switch (soilInput.getType()) {
            case FOREST -> new ForestSoil(soilInput);
            case DESERT -> new DesertSoil(soilInput);
            case SWAMP -> new SwampSoil(soilInput);
            case TUNDRA -> new TundraSoil(soilInput);
            case GRASSLAND -> new GrasslandSoil(soilInput);
            default -> throw new UnknownSoilException(soilInput.getType());
        };
    }

}
