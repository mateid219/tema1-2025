package commands.environment;

// import Events.Event;

import commands.Command;
import exceptions.DoesNotAffectException;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.events.weather.WeatherChangeVisitor;
import simulation.events.weather.WeatherEvent;
// import java.util.Queue;


public abstract class EnvironmentCommand extends Command {

    private static final int WEATHER_EVENT_DURATION = 2;

    private static final String SUCCESS_MESSAGE = "The weather has changed.";


    public EnvironmentCommand(final CommandInput commandInput) {
        super(commandInput);
    }
    protected final void startWeatherChange(final Simulation simulation,
                                      final WeatherChangeVisitor weatherChangeVisitor) {
        new WeatherEvent(timestamp, weatherChangeVisitor).takeEffect(simulation);
    }
    protected final void endWeatherChange(final Simulation simulation,
                                    final WeatherChangeVisitor weatherChangeVisitor) {
        simulation.addEvent(
                new WeatherEvent(timestamp + WEATHER_EVENT_DURATION, weatherChangeVisitor)
        );
    }
    /**
     * Changes the weather as specified at construction.
     * Makes sure the weather changes are reverted when checking after at least 2 timestamps.
     * Always sets a valid message.
     * @param simulation <p>the simulation on which the weather is changed.
                            Might not be started.
     *                   </p>
     */
    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        try {
            weatherCommand(simulation);
            message = SUCCESS_MESSAGE;
        } catch (DoesNotAffectException e) {
            message = e.getMessage();
        }
    }
    abstract void weatherCommand(Simulation simulation) throws DoesNotAffectException;
}
