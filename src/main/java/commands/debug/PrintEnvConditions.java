package commands.debug;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class PrintEnvConditions extends DebugCommand {

    public PrintEnvConditions(final CommandInput commandInput) {
        super(commandInput);
    }
    @Override
    public void doExecute(Simulation simulation) {
        commandObjectOutput = simulation.printEnvConditions();
    }
}
