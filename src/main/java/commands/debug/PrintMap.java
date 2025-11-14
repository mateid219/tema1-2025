package commands.debug;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class PrintMap extends DebugCommand {
    public PrintMap(CommandInput commandInput) {
        super(commandInput);
    }

    @Override
    public void doExecute(Simulation simulation) {
        commandArrayOutput = simulation.printMap();
    }
}
