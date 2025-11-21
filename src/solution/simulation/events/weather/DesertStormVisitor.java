package simulation.events.weather;

import entities.air.DesertAir;

public record DesertStormVisitor(boolean newDesertStorm) implements WeatherChangeVisitor {
    @Override
    public boolean visitDesertAir(final DesertAir air) {
        air.setDesertStorm(newDesertStorm);
        return true;
    }
}
