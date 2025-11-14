package commands;

import commands.debug.GetEnergyStatus;
import commands.debug.PrintEnvConditions;
import commands.debug.PrintKnowledgeBase;
import commands.debug.PrintMap;
import commands.environment.EnvironmentCommand;
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
    /**
     * Guarantees created commands have valid names.
     * @return command of appropriate type
     * @throws AssertionError if command name is unknown
     */
    public static Command createCommand(final CommandInput commandInput)
            throws UnknownCommandException {
        String commandName = commandInput.getCommand();
        return switch (commandName) {
            case CommandConstants.START_SIMULATION ->
                new StartSimulation(commandInput);
            case CommandConstants.END_SIMULATION ->
                new EndSimulation(commandInput);
            case CommandConstants.PRINT_ENV_CONDITIONS ->
                new PrintEnvConditions(commandInput);
            case CommandConstants.GET_ENERGY_STATUS ->
                new GetEnergyStatus(commandInput);
            case CommandConstants.PRINT_MAP ->
                new PrintMap(commandInput);
            case CommandConstants.PRINT_KNOWLEDGE_BASE ->
                new PrintKnowledgeBase(commandInput);
            case CommandConstants.CHANGE_WEATHER ->
                new EnvironmentCommand(commandInput);
            case CommandConstants.MOVE_ROBOT ->
                new MoveCommand(commandInput);
            case CommandConstants.LEARN_FACT ->
                new LearnCommand(commandInput);
            case CommandConstants.SCAN_OBJECT ->
                new ScanCommand(commandInput);
            case CommandConstants.IMPROVE_ENVIRONMENT ->
                new ImproveCommand(commandInput);
            case CommandConstants.RECHARGE_BATTERY ->
                new RechargeCommand(commandInput);
            default -> throw new UnknownCommandException();
        };
    }
}
