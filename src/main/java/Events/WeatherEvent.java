package Events;

import Simulation.Simulation;
import commands.EnvironmentCommand;
import entities.air.*;

import java.util.ArrayList;

import static entities.air.Air.*;

public class WeatherEvent extends Event {

    private String type;
    private int numberOfHikers;
    private String newSeason;
    private double windSpeed;
    private double rainfall;

    public WeatherEvent() { }
    public WeatherEvent(int timestamp) {
        super(timestamp);
    }
    public WeatherEvent(int timestamp, EnvironmentCommand.EnvironmentParams environmentParams,
                        boolean endFlag) {
        super(timestamp);
        type = environmentParams.getType();
        numberOfHikers = endFlag ? 0 : environmentParams.getNumberOfHikers();
        newSeason = endFlag ? "default" : environmentParams.getNewSeason();
        windSpeed = endFlag ? 0.0 : environmentParams.getWindspeed();
        rainfall = endFlag ? 0.0 : environmentParams.getRainfall();
    }
    public void takeEffect(Simulation simulation) {
        ArrayList<Air> airArrayList = simulation.getTerritorySectionParams().getAir();
        for (Air air : airArrayList) {
            if (MOUNTAIN_AIR.equals(air.getType()) && MOUNTAIN_AIR.equals(type)) {
                Mountain mountainAir = (Mountain) air;
                mountainAir.setNumberOfHikers(numberOfHikers);
            }
            else if (DESERT_AIR.equals(air.getType()) && DESERT_AIR.equals(type)) {
                Desert desertAir = (Desert) air;
                desertAir.setDesertStorm(! desertAir.isDesertStorm());
            }
            else if (TEMPERATE_AIR.equals(air.getType()) && TEMPERATE_AIR.equals(type)) {
                Temperate temperateAir = (Temperate) air;
                temperateAir.setNewSeason(newSeason);
            }
            else if (POLAR_AIR.equals(air.getType()) && POLAR_AIR.equals(type)) {
                Polar polarAir = (Polar) air;
                polarAir.setWindspeed(windSpeed);
            }
            else if (TROPICAL_AIR.equals(air.getType()) && TROPICAL_AIR.equals(type)) {
                Tropical tropicalAir = (Tropical) air;
                tropicalAir.setRainfall(rainfall);
            }
        }
    }
}
