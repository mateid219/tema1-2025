package commands.debug;

import fileio.CommandInput;
import lombok.NoArgsConstructor;
import simulation.Simulation;

@NoArgsConstructor
public final class GetEnergyStatus extends DebugCommand {
    private static final String ENERGY_STATUS_FORMAT = "TerraBot has %d energy points left.";
    public GetEnergyStatus(final CommandInput commandInput) {
        super(commandInput);
    }
    @Override
    public void doExecute(final Simulation simulation) {
        message = String.format(ENERGY_STATUS_FORMAT, simulation.getTerraBot().getEnergyPoints());
    }
}
