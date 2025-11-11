package commands;

import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class CommandArrayList {
    @Getter @Setter private ArrayList<Command> commands;

    private static String commandType(final String command) {
        if (SimulationCommand.isSimulationCommand(command)) {
            return "Simulation";
        }
        if (DebugCommand.isDebugCommand(command)) {
            return "Debug";
        }
        if (RobotCommand.isRobotCommand(command)) {
            return "Robot";
        }
        if (EnvironmentCommand.isEnvironmentCommand(command)) {
            return "Environment";
        }
        return null;
    }

    public CommandArrayList() { }
    public CommandArrayList(final ArrayList<CommandInput> commandInputArrayList) {
        commands = new ArrayList<Command>();
        for (CommandInput commandInput : commandInputArrayList) {
            String commandName = commandInput.getCommand();
            Command command = null;
            switch (commandType(commandName)) {
                case "Simulation":
                    command = new SimulationCommand(commandInput);
                    break;
                case "Debug":
                    command = new DebugCommand(commandInput);
                    break;
                case "Robot":
                    command = new RobotCommand(commandInput);
                    break;
                case "Environment":
                    command = new EnvironmentCommand(commandInput);
                    break;
                default:
                    break;
            }
            commands.add(command);
        }
    }
}
