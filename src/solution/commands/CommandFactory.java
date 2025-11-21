package commands;

import commands.debug.GetEnergyStatus;
import commands.debug.PrintEnvConditions;
import commands.debug.PrintKnowledgeBase;
import commands.debug.PrintMap;
import commands.environment.DesertStorm;
import commands.environment.NewSeason;
import commands.environment.PeopleHiking;
import commands.environment.PolarStorm;
import commands.environment.Rainfall;
import commands.robot.ImproveCommand;
import commands.robot.LearnCommand;
import commands.robot.MoveCommand;
import commands.robot.RechargeCommand;
import commands.robot.ScanCommand;
import commands.simulation.EndSimulation;
import commands.simulation.StartSimulation;
import exceptions.UnknownCommandException;
import fileio.CommandInput;

public final class CommandFactory {
    private CommandFactory() throws AssertionError {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    private static final String PRINT_ENV_CONDITIONS = "printEnvConditions";
    private static final String PRINT_MAP = "printMap";
    private static final String PRINT_KNOWLEDGE_BASE = "printKnowledgeBase";
    private static final String GET_ENERGY_STATUS = "getEnergyStatus";
    private static final String CHANGE_WEATHER = "changeWeatherConditions";
    private static final String MOVE_ROBOT = "moveRobot";
    private static final String RECHARGE_BATTERY = "rechargeBattery";
    private static final String SCAN_OBJECT = "scanObject";
    private static final String LEARN_FACT = "learnFact";
    private static final String IMPROVE_ENVIRONMENT = "improveEnvironment";
    private static final String START_SIMULATION = "startSimulation";
    private static final String END_SIMULATION = "endSimulation";

    private static final String DESERT_STORM = "desertStorm";
    private static final String PEOPLE_HIKING = "peopleHiking";
    private static final String NEW_SEASON = "newSeason";
    private static final String POLAR_STORM = "polarStorm";
    private static final String RAINFALL = "rainfall";


    /**
     * Guarantees created commands have valid names.
     * @return command of appropriate type
     * @throws AssertionError if command name is unknown
     */
    public static Command createCommand(final CommandInput commandInput)
            throws UnknownCommandException {
        String commandName = commandInput.getCommand();
        return switch (commandName) {
            case START_SIMULATION -> new StartSimulation(commandInput);
            case END_SIMULATION -> new EndSimulation(commandInput);
            case PRINT_ENV_CONDITIONS -> new PrintEnvConditions(commandInput);
            case GET_ENERGY_STATUS -> new GetEnergyStatus(commandInput);
            case PRINT_MAP -> new PrintMap(commandInput);
            case PRINT_KNOWLEDGE_BASE -> new PrintKnowledgeBase(commandInput);
            case MOVE_ROBOT -> new MoveCommand(commandInput);
            case LEARN_FACT -> new LearnCommand(commandInput);
            case SCAN_OBJECT -> new ScanCommand(commandInput);
            case RECHARGE_BATTERY -> new RechargeCommand(commandInput);
            case IMPROVE_ENVIRONMENT -> new ImproveCommand(commandInput);
            case CHANGE_WEATHER -> switch (commandInput.getType().trim()) {
                case DESERT_STORM -> new DesertStorm(commandInput);
                case PEOPLE_HIKING -> new PeopleHiking(commandInput);
                case NEW_SEASON -> new NewSeason(commandInput);
                case POLAR_STORM -> new PolarStorm(commandInput);
                case RAINFALL -> new Rainfall(commandInput);
                default -> throw new UnknownCommandException();
            };
            default -> throw new UnknownCommandException();
        };
    }
}
