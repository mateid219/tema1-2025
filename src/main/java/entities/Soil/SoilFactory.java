package entities.Soil;

import fileio.SoilInput;

public final class SoilFactory {
    private SoilFactory() throws AssertionError {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    /**
     * Creates a soil entity.
     * @return the newly created soil
     */
    public static Soil createSoil(final SoilInput soilInput) {
        return switch (soilInput.getType()) {
            case "ForestSoil" -> new ForestSoil(soilInput);
            case "DesertSoil" -> new DesertSoil(soilInput);
            case "SwampSoil" -> new SwampSoil(soilInput);
            case "TundraSoil" -> new TundraSoil(soilInput);
            case "GrasslandSoil" -> new GrasslandSoil(soilInput);
            default -> null;
        };
    }

}
