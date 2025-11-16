package commands;

public final class CommandConstants {
    private CommandConstants() throws AssertionError {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }

    public static final String ERROR_CHARGING = "ERROR: Robot still charging."
            + " Cannot perform action";
    /**
     * Debug commands
     */
    public static final String PRINT_ENV_CONDITIONS = "printEnvConditions";
    public static final String PRINT_MAP = "printMap";
    public static final String PRINT_KNOWLEDGE_BASE = "printKnowledgeBase";
    public static final String GET_ENERGY_STATUS = "getEnergyStatus";



    public static final String CHANGE_WEATHER = "changeWeatherConditions";

    /**
     *  Robot commands
     */
    public static final String MOVE_ROBOT = "moveRobot";
    public static final String RECHARGE_BATTERY = "rechargeBattery";
    public static final String SCAN_OBJECT = "scanObject";
    public static final String LEARN_FACT = "learnFact";
    public static final String IMPROVE_ENVIRONMENT = "improveEnvironment";


    /**
     * Simulation commands
     */
    public static final String START_SIMULATION = "startSimulation";
    public static final String END_SIMULATION = "endSimulation";
    public static final String SUCCESS_STARTED = "Simulation has started.";
    public static final String SUCCESS_ENDED = "Simulation has ended.";

    public static final String ERROR_ALREADY_STARTED = "ERROR: Simulation already started."
            + " Cannot perform action";
    public static final String ERROR_NOT_STARTED = "ERROR: Simulation not started."
            + " Cannot perform action";

    /**
     * Environment commands
     */
    public static final String SUCCESS_MESSAGE = "The weather has changed.";
    public static final String ERROR_DOES_NOT_AFFECT = "ERROR: The weather change does not affect"
            + " the environment. Cannot perform action";

    public static final String DESERT_STORM = "desertStorm ";
    public static final String PEOPLE_HIKING = "peopleHiking ";
    public static final String NEW_SEASON = "newSeason ";
    public static final String POLAR_STORM = "polarStorm ";
    public static final String RAINFALL = "rainfall ";
}
