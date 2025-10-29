package commands;

import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class CommandArrayList {
    @Getter @Setter private ArrayList<Command> commands;

    static final String commandType(final String command) {
        if (SimulationCommand.isSimulationCommand(command)) {
            return "Simulation";
        }
        return "Debug";
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
                default:
                    break;
            }
            commands.add(command);
        }
    }
}
