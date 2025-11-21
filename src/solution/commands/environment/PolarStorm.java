package commands.environment;

import exceptions.DoesNotAffectException;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.events.weather.PolarStormVisitor;

public final class PolarStorm extends EnvironmentCommand {
    private final double windSpeed;
    public PolarStorm(final CommandInput commandInput) {
        super(commandInput);
        this.windSpeed = commandInput.getWindSpeed();
    }

    @Override
    void weatherCommand(final Simulation simulation) throws DoesNotAffectException {
        startWeatherChange(simulation, new PolarStormVisitor(windSpeed));
        endWeatherChange(simulation, new PolarStormVisitor(0.0));
    }
}
