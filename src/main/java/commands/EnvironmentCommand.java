package commands;

import Events.Event;
import Events.WeatherEvent;
import Simulation.Simulation;
import entities.air.Air;
import fileio.CommandInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Queue;

import static entities.air.Air.*;

public final class EnvironmentCommand extends Command {

    public final class EnvironmentParams {
        @Getter private String type;
        @Getter private double rainfall;
        @Getter private double windspeed;
        @Getter private String newSeason;
        @Getter private int numberOfHikers;
    }
    EnvironmentParams environmentParams;

    private static final String ENV_COMMAND = "changeWeatherConditions";

    private static final String SUCCESS_MESSAGE = "The weather has changed.";
    private static final String ERROR_DOES_NOT_AFFECT = "ERROR: The weather change does not affect" +
                                                  " the environment. Cannot perform action";

    private static final String DESERT_STORM = "desertStorm";
    private static final String PEOPLE_HIKING = "peopleHiking";
    private static final String NEW_SEASON = "newSeason";
    private static final String POLAR_STORM = "polarStorm";
    private static final String RAINFALL = "rainfall";
    private static final int DURATION = 2;

    public static boolean isEnvironmentCommand(final String type) {
        return ENV_COMMAND.equals(type);
    }

    public EnvironmentCommand() { }
    public EnvironmentCommand(CommandInput commandInput) {
        super(commandInput);
        environmentParams = new EnvironmentParams();
        environmentParams.type = commandInput.getType();
        if (PEOPLE_HIKING.equals(environmentParams.type)) {
            environmentParams.numberOfHikers = commandInput.getNumberOfHikers();
        }
        if (NEW_SEASON.equals(environmentParams.type)) {
            environmentParams.newSeason = commandInput.getSeason();
        }
        if (POLAR_STORM.equals(environmentParams.type)) {
            environmentParams.windspeed = commandInput.getWindSpeed();
        }
        if (RAINFALL.equals(environmentParams.type)) {
            environmentParams.rainfall = commandInput.getRainfall();
        }
    }
    private String typedWeatherEvent(Simulation simulation, String changeType, String airType) {
        if (! changeType.equals(environmentParams.type)) {
            return message;
        }
        Queue<Event> eventQueue = simulation.getEventQueue();
        ArrayList<Air> airList = simulation.getTerritorySectionParams().getAir();
        if (airList.stream().noneMatch(air -> airType.equals(air.getType()))) {
            return ERROR_DOES_NOT_AFFECT;
        }
        environmentParams.type = airType;
        new WeatherEvent(timestamp, environmentParams, false).takeEffect(simulation);
        eventQueue.add(new WeatherEvent(timestamp + DURATION, environmentParams, true));
        return SUCCESS_MESSAGE;
    }
    public void execute(Simulation simulation) {
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        message = typedWeatherEvent(simulation, PEOPLE_HIKING, MOUNTAIN_AIR);
        message = typedWeatherEvent(simulation, DESERT_STORM, DESERT_AIR);
        message = typedWeatherEvent(simulation, NEW_SEASON, TEMPERATE_AIR);
        message = typedWeatherEvent(simulation, POLAR_STORM, POLAR_AIR);
        message = typedWeatherEvent(simulation, RAINFALL, TROPICAL_AIR);
    }
}
