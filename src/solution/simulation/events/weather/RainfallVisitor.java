package simulation.events.weather;

import entities.air.TropicalAir;

public record RainfallVisitor(double newRainfall) implements WeatherChangeVisitor {
    @Override
    public boolean visitTropicalAir(final TropicalAir air) {
        air.setRainfall(newRainfall);
        return true;
    }
}
