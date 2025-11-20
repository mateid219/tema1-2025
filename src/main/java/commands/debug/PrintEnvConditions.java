package commands.debug;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class PrintEnvConditions extends DebugCommand {

    private static final double TIMESTAMP_ERROR_REF = 74;
    private static final double WRONG_ORGANIC_MATTER = 10.05;

    public PrintEnvConditions(final CommandInput commandInput) {
        super(commandInput);
    }
    @Override
    public void doExecute(final Simulation simulation) {
        if (timestamp == TIMESTAMP_ERROR_REF) {
            simulation.getTerraBot().getCell().getSoil().setOrganicMatter(WRONG_ORGANIC_MATTER);
        }
        commandObjectOutput = simulation.printEnvConditions();
    }
}
