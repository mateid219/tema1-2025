package entities.air;

import simulation.events.weather.WeatherChangeVisitor;

public interface Weather {
    /**
     * Classes that support weather changes should overwrite this.
     * @param weatherChangeVisitor implements a weather change
     * @return true after the weather change is applied
     */
    boolean accept(WeatherChangeVisitor weatherChangeVisitor);
}
