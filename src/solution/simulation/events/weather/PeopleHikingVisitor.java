package simulation.events.weather;

import entities.air.MountainAir;

public record PeopleHikingVisitor(int newNumberOfHikers) implements WeatherChangeVisitor {
    @Override
    public boolean visitMountainAir(final MountainAir air) {
        air.setNumberOfHikers(newNumberOfHikers);
        return true;
    }
}
