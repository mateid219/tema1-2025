package simulation.events;

import commands.environment.EnvironmentCommand;
import entities.air.Air;
import entities.air.Desert;
import entities.air.Mountain;
import entities.air.Polar;
import entities.air.Temperate;
import entities.air.Tropical;
import lombok.NoArgsConstructor;
import simulation.EnvironmentMap;
import simulation.Simulation;
import simulation.terrabot.TerraBot;

import java.util.Queue;

import static entities.air.AirFactory.DESERT_AIR;
import static entities.air.AirFactory.MOUNTAIN_AIR;
import static entities.air.AirFactory.POLAR_AIR;
import static entities.air.AirFactory.TEMPERATE_AIR;
import static entities.air.AirFactory.TROPICAL_AIR;

@NoArgsConstructor
public final class WeatherEvent extends Event {

    private String type;
    private int numberOfHikers;
    private String newSeason;
    private double windSpeed;
    private double rainfall;

    public WeatherEvent(final int timestamp) {
        super(timestamp);
        priority = EventPriorities.AIR_EVENT.ordinal();
    }
    public WeatherEvent(final int timestamp,
                        final EnvironmentCommand.EnvironmentParams environmentParams,
                        final boolean endFlag) {
        super(timestamp);
        type = environmentParams.getType();
        numberOfHikers = endFlag ? 0 : environmentParams.getNumberOfHikers();
        newSeason = endFlag ? "default" : environmentParams.getNewSeason();
        windSpeed = endFlag ? 0.0 : environmentParams.getWindSpeed();
        rainfall = endFlag ? 0.0 : environmentParams.getRainfall();
    }
    @Override
    public void takeEffect(final Simulation simulation) {
        TerraBot terraBot = simulation.getTerraBot();
        Queue<Event> eventQueue = simulation.getEventQueue();
        EnvironmentMap map = simulation.getEnvironmentMap();
        for (int x = 0; x < map.getWidth(); ++x) {
            for (int y = 0; y < map.getHeight(); ++y) {
                Air air = map.cellAt(x, y).getAir();
                if (MOUNTAIN_AIR.equals(air.getType()) && MOUNTAIN_AIR.equals(type)) {
                    Mountain mountainAir = (Mountain) air;
                    mountainAir.setNumberOfHikers(numberOfHikers);
                } else if (DESERT_AIR.equals(air.getType()) && DESERT_AIR.equals(type)) {
                    Desert desertAir = (Desert) air;
                    desertAir.setDesertStorm(!desertAir.isDesertStorm());
                } else if (TEMPERATE_AIR.equals(air.getType()) && TEMPERATE_AIR.equals(type)) {
                    Temperate temperateAir = (Temperate) air;
                    temperateAir.setNewSeason(newSeason);
                } else if (POLAR_AIR.equals(air.getType()) && POLAR_AIR.equals(type)) {
                    Polar polarAir = (Polar) air;
                    polarAir.setWindspeed(windSpeed);
                } else if (TROPICAL_AIR.equals(air.getType()) && TROPICAL_AIR.equals(type)) {
                    Tropical tropicalAir = (Tropical) air;
                    tropicalAir.setRainfall(rainfall);
                }
            }
        }
    }
}
