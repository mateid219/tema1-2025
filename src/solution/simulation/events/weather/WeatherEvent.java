package simulation.events.weather;

import entities.air.Air;
import exceptions.DoesNotAffectException;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.environmentMap.EnvironmentMap;
import simulation.events.Event;


public final class WeatherEvent extends Event {

    private final WeatherChangeVisitor weatherChangeVisitor;

    public WeatherEvent(final int timestamp,
                        final WeatherChangeVisitor weatherChangeVisitor) {
        super(timestamp);
        this.weatherChangeVisitor = weatherChangeVisitor;
    }
    @Override
    public void takeEffect(final Simulation simulation) throws DoesNotAffectException {
        EnvironmentMap map = simulation.getEnvironmentMap();
        boolean doesAffect = false;
        for (Cell cell : map.getCells()) {
            Air air = cell.getAir();
            doesAffect |= air.accept(weatherChangeVisitor);
        }
        if (!doesAffect) {
            throw new DoesNotAffectException();
        }
    }
}
