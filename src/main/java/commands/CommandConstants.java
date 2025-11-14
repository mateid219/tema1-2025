package commands;

public interface CommandConstants {
    static final String ERROR_CHARGING = "ERROR: Robot still charging. Cannot perform action";
    /**
     * Debug commands
     */
    static final String PRINT_ENV_CONDITIONS = "printEnvConditions";
    static final String PRINT_MAP = "printMap";
    static final String PRINT_KNOWLEDGE_BASE = "printKnowledgeBase";
    static final String GET_ENERGY_STATUS = "getEnergyStatus";



    static final String CHANGE_WEATHER = "changeWeatherConditions";

    /**
     *  Robot commands
     */
    static final String MOVE_ROBOT = "moveRobot";
    static final String RECHARGE_BATTERY = "rechargeBattery";
    static final String SCAN_OBJECT = "scanObject";
    static final String LEARN_FACT = "learnFact";
    static final String IMPROVE_ENVIRONMENT = "improveEnvironment";


    /**
     * Simulation commands
     */
    static final String START_SIMULATION = "startSimulation";
    static final String END_SIMULATION = "endSimulation";
    static final String SUCCESS_STARTED = "Simulation has started.";
    static final String SUCCESS_ENDED = "Simulation has ended.";

    static final String ERROR_ALREADY_STARTED = "ERROR: Simulation already started."
            + " Cannot perform action";
    public static final String ERROR_NOT_STARTED = "ERROR: Simulation not started. Cannot perform action";

    /**
     * Environment commands
     */
    static final String SUCCESS_MESSAGE = "The weather has changed.";
    static final String ERROR_DOES_NOT_AFFECT = "ERROR: The weather change does not affect"
            + " the environment. Cannot perform action";

    static final String DESERT_STORM = "desertStorm ";
    static final String PEOPLE_HIKING = "peopleHiking ";
    static final String NEW_SEASON = "newSeason ";
    static final String POLAR_STORM = "polarStorm ";
    static final String RAINFALL = "rainfall ";
}
