package commands.environment;

// import Events.Event;

import commands.Command;
import commands.CommandConstants;
import entities.air.AirFactory;
import fileio.CommandInput;
import lombok.Getter;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.WeatherEvent;

import java.util.ArrayList;
// import java.util.Queue;


public final class EnvironmentCommand extends Command {

    private static final int WEATHER_EVENT_DURATION = 2;
    public static final class EnvironmentParams {
        @Getter private String type;
        @Getter private double rainfall;
        @Getter private double windSpeed;
        @Getter private String newSeason;
        @Getter private int numberOfHikers;
    }
    private @Getter EnvironmentParams environmentParams;

    public EnvironmentCommand() { }
    public EnvironmentCommand(final CommandInput commandInput) {
        super(commandInput);
        environmentParams = new EnvironmentParams();
        environmentParams.type = commandInput.getType();
        if (CommandConstants.PEOPLE_HIKING.contains(environmentParams.type)) {
            environmentParams.numberOfHikers = commandInput.getNumberOfHikers();
        }
        if (CommandConstants.NEW_SEASON.contains(environmentParams.type)) {
            environmentParams.newSeason = commandInput.getSeason();
        }
        if (CommandConstants.POLAR_STORM.contains(environmentParams.type)) {
            environmentParams.windSpeed = commandInput.getWindSpeed();
        }
        if (CommandConstants.RAINFALL.contains(environmentParams.type)) {
            environmentParams.rainfall = commandInput.getRainfall();
        }
    }
    private String typedWeatherEvent(final Simulation simulation, final String changeType,
                                     final String airType) {
        if (!changeType.contains(environmentParams.type)) {
            return message;
        }
        ArrayList<Cell> cellList = simulation.getEnvironmentMap().getCells();
        cellList.removeIf(cell -> cell.getAir() == null);

        if (cellList.stream().noneMatch(cell -> airType.equals(cell.getAir().getType()))) {
            return CommandConstants.ERROR_DOES_NOT_AFFECT;
        }
        environmentParams.type = airType;
        new WeatherEvent(timestamp, environmentParams, false).takeEffect(simulation);
        simulation.addEvent(new WeatherEvent(timestamp + WEATHER_EVENT_DURATION,
                                        environmentParams, true)
        );
        return CommandConstants.SUCCESS_MESSAGE;
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
            message = CommandConstants.ERROR_NOT_STARTED;
            return;
        }
        message = typedWeatherEvent(simulation, CommandConstants.PEOPLE_HIKING,
                                    AirFactory.MOUNTAIN_AIR);
        message = typedWeatherEvent(simulation, CommandConstants.DESERT_STORM,
                                    AirFactory.DESERT_AIR);
        message = typedWeatherEvent(simulation, CommandConstants.NEW_SEASON,
                                    AirFactory.TEMPERATE_AIR);
        message = typedWeatherEvent(simulation, CommandConstants.POLAR_STORM,
                                    AirFactory.POLAR_AIR);
        message = typedWeatherEvent(simulation, CommandConstants.RAINFALL,
                                    AirFactory.TROPICAL_AIR);
    }
}
