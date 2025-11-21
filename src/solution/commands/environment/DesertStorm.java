package commands.environment;

import exceptions.DoesNotAffectException;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.events.weather.DesertStormVisitor;

public final class DesertStorm extends EnvironmentCommand {
    public DesertStorm(final CommandInput commandInput) {
        super(commandInput);
    }
    @Override
    void weatherCommand(final Simulation simulation) throws DoesNotAffectException {
        startWeatherChange(simulation, new DesertStormVisitor(true));
        endWeatherChange(simulation, new DesertStormVisitor(false));
    }
}
