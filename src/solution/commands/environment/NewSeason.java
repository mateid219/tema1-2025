package commands.environment;

import exceptions.DoesNotAffectException;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.events.weather.NewSeasonVisitor;

public final class NewSeason extends EnvironmentCommand {
    private final String season;
    public NewSeason(final CommandInput commandInput) {
        super(commandInput);
        this.season = commandInput.getSeason();
    }

    @Override
    void weatherCommand(final Simulation simulation) throws DoesNotAffectException {
        startWeatherChange(simulation, new NewSeasonVisitor(season));
        endWeatherChange(simulation, new NewSeasonVisitor(""));
    }
}
