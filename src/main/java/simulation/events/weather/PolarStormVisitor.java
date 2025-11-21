package simulation.events.weather;

import entities.air.Polar;

public record PolarStormVisitor(double newWindSpeed) implements WeatherChangeVisitor {
    @Override
    public boolean visitPolarAir(final Polar air) {
        air.setWindspeed(newWindSpeed);
        return true;
    }
}
