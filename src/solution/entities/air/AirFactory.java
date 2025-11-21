package entities.air;

import fileio.AirInput;

public final class AirFactory {
    private AirFactory() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    public static final String MOUNTAIN_AIR = "MountainAir";
    public static final String DESERT_AIR = "DesertAir";
    public static final String TEMPERATE_AIR = "TemperateAir";
    public static final String POLAR_AIR = "PolarAir";
    public static final String TROPICAL_AIR = "TropicalAir";
    /**
     * Creates an air entity.
     * @return the newly created air
     */
    public static Air createAir(final AirInput airInput) {
        return switch (airInput.getType()) {
            case MOUNTAIN_AIR -> new MountainAir(airInput);
            case DESERT_AIR -> new DesertAir(airInput);
            case TEMPERATE_AIR -> new TemperateAir(airInput);
            case POLAR_AIR -> new PolarAir(airInput);
            case TROPICAL_AIR -> new TropicalAir(airInput);
            default -> null;
        };
    }
}
