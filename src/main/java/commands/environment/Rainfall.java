package commands.environment;

import exceptions.DoesNotAffectException;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.events.weather.RainfallVisitor;

public final class Rainfall extends EnvironmentCommand {
    private final double rainfall;
    public Rainfall(final CommandInput commandInput) {
        super(commandInput);
        this.rainfall = commandInput.getRainfall();
    }

    @Override
    void weatherCommand(final Simulation simulation) throws DoesNotAffectException {
        startWeatherChange(simulation, new RainfallVisitor(rainfall));
        endWeatherChange(simulation, new RainfallVisitor(0.0));
    }
}
