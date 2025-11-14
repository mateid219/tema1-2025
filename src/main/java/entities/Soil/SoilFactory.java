package entities.Soil;

import fileio.SoilInput;

public final class SoilFactory {
    private SoilFactory() throws AssertionError {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    public Soil createSoil(SoilInput soilInput) {
        return switch (soilInput.getType()) {
            case "ForestSoil" -> new ForestSoil(soilInput, x, y);
            case "DesertSoil" -> new DesertSoil(soilInput, x, y);
            case "SwampSoil" -> new SwampSoil(soilInput, x, y);
            case "TundraSoil" -> new TundraSoil(soilInput, x, y);
            case "GrasslandSoil" -> new GrasslandSoil(soilInput, x, y);
            default -> null;
        };
    }

}
