package simulation.terrabot.improvements;

import exceptions.UnknownImprovementException;
import simulation.environmentMap.Cell;

public final class ImprovementApplier {

    private ImprovementApplier() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    private static final String PLANT_VEGETATION_SUCCESS_FORMAT =
            "The %s was planted successfully.";
    private static final String INCREASE_HUMIDITY_SUCCESS_FORMAT =
            "The humidity was successfully increased using %s";
    private static final String INCREASE_MOISTURE_SUCCESS_FORMAT =
            "The moisture was successfully increased using %s";
    private static final String FERTILIZE_SOIL_SUCCESS_FORMAT =
            "The soil was successfully fertilized using %s";

    private static final double OXYGEN_INCREASE = 0.3;
    private static final double ORGANIC_MATTER_INCREASE = 0.3;
    private static final double HUMIDITY_INCREASE = 0.2;
    private static final double WATER_RETENTION_INCREASE = 0.2;

    private static final String PLANT_VEGETATION = "plantVegetation";
    private static final String FERTILIZE_SOIL = "fertilizeSoil";
    private static final String INCREASE_HUMIDITY = "increaseHumidity";
    private static final String INCREASE_MOISTURE = "increaseMoisture";

    /**
     *
     * @param type the kind of improveEnvironment command.
     * @param cell the cell on which the environment is improved.
     * @return The improvement success message
     * @throws UnknownImprovementException supplies the type of the erroneous improvement
     */
    public static String improveEnvironment(final String type, final Cell cell)
            throws UnknownImprovementException {
        return switch (type) {
            case PLANT_VEGETATION -> {
                cell.getAir().increaseOxygen(OXYGEN_INCREASE);
                yield PLANT_VEGETATION_SUCCESS_FORMAT;
            }
            case INCREASE_HUMIDITY -> {
                cell.getAir().increaseHumidity(HUMIDITY_INCREASE);
                yield INCREASE_HUMIDITY_SUCCESS_FORMAT;
            }
            case FERTILIZE_SOIL -> {
                cell.getSoil().fertilize(ORGANIC_MATTER_INCREASE);
                yield FERTILIZE_SOIL_SUCCESS_FORMAT;
            }
            case INCREASE_MOISTURE -> {
                cell.getSoil().increaseWaterRetention(WATER_RETENTION_INCREASE);
                yield INCREASE_MOISTURE_SUCCESS_FORMAT;
            }
            default -> throw new UnknownImprovementException(type);
        };
    }
}
