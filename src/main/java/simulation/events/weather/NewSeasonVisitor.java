package simulation.events.weather;

import entities.air.Temperate;

public record NewSeasonVisitor(String newSeason) implements WeatherChangeVisitor {
    @Override
    public boolean visitTemperateAir(final Temperate air) {
        air.setNewSeason(newSeason);
        return true;
    }
}
