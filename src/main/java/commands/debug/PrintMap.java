package commands.debug;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class PrintMap extends DebugCommand {
    public PrintMap(final CommandInput commandInput) {
        super(commandInput);
    }

    @Override
    public void doExecute(final Simulation simulation) {
        commandArrayOutput = simulation.getEnvironmentMap().buildMapOutput();
    }
}
