package simulation.events.weather;

import entities.air.Mountain;

public record PeopleHikingVisitor(int newNumberOfHikers) implements WeatherChangeVisitor {
    @Override
    public boolean visitMountainAir(final Mountain air) {
        air.setNumberOfHikers(newNumberOfHikers);
        return true;
    }
}
