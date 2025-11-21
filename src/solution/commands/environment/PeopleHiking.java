package commands.environment;

import exceptions.DoesNotAffectException;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.events.weather.PeopleHikingVisitor;

public final class PeopleHiking extends EnvironmentCommand {
    private final int numberOfHikers;
    public PeopleHiking(final CommandInput commandInput) {
        super(commandInput);
        this.numberOfHikers = commandInput.getNumberOfHikers();
    }

    @Override
    void weatherCommand(final Simulation simulation) throws DoesNotAffectException {
        startWeatherChange(simulation, new PeopleHikingVisitor(numberOfHikers));
        endWeatherChange(simulation, new PeopleHikingVisitor(0));
    }
}
