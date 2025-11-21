package simulation.events.weather;

import entities.air.TemperateAir;

public record NewSeasonVisitor(String newSeason) implements WeatherChangeVisitor {
    @Override
    public boolean visitTemperateAir(final TemperateAir air) {
        air.setNewSeason(newSeason);
        return true;
    }
}
