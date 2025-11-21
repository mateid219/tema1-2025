package simulation.events.weather;

import entities.air.PolarAir;

public record PolarStormVisitor(double newWindSpeed) implements WeatherChangeVisitor {
    @Override
    public boolean visitPolarAir(final PolarAir air) {
        air.setWindspeed(newWindSpeed);
        return true;
    }
}
