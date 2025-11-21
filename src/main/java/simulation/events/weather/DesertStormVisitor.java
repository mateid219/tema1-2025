package simulation.events.weather;

import entities.air.Desert;

public record DesertStormVisitor(boolean newDesertStorm) implements WeatherChangeVisitor {
    @Override
    public boolean visitDesertAir(final Desert air) {
        air.setDesertStorm(newDesertStorm);
        return true;
    }
}
