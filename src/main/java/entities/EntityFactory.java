package entities;

public final class EntityFactory {
    private EntityFactory() throws AssertionError {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }/*
    public Soil createEntity(SoilInput soilInput) {
        return switch (soilInput.getType()) {
            case "ForestSoil" -> new ForestSoil(soilInput);
            case "DesertSoil" -> new DesertSoil(soilInput);
            case "SwampSoil" -> new SwampSoil(soilInput);
            case "TundraSoil" -> new TundraSoil(soilInput);
            case "GrasslandSoil" -> new GrasslandSoil(soilInput);
            default -> null;
        };
    }*/
}
