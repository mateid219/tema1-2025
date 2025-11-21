package simulation.events.weather;

import entities.air.Tropical;

public record RainfallVisitor(double newRainfall) implements WeatherChangeVisitor {
    @Override
    public boolean visitTropicalAir(final Tropical air) {
        air.setRainfall(newRainfall);
        return true;
    }
}
