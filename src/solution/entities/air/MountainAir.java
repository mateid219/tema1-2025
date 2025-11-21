package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import simulation.events.weather.WeatherChangeVisitor;

public final class MountainAir extends Air {
    private double altitude;
    @Setter @Getter
    private int numberOfHikers;

    private static final double MAX_SCORE = 78.0;
    private static final double OXYGEN_COFF = 2.0;
    private static final double ALTITUDE_COFF = 0.0005;
    private static final double HUMIDITY_COFF = 0.6;
    private static final double HIKERS_COFF = 0.6;

    public MountainAir(final AirInput airInput) {
        super(airInput);
        numberOfHikers = 0;
        maxScore = MAX_SCORE;
        altitude = airInput.getAltitude();
    }

    @Override
    public boolean accept(final WeatherChangeVisitor weatherChangeVisitor) {
        return weatherChangeVisitor.visitMountainAir(this);
    }

    @Override
    public double calculateScore() {
        double oxygenFactor = oxygenLevel - altitude * ALTITUDE_COFF;
        double oxygenScore = oxygenFactor * OXYGEN_COFF;
        double humidityScore = humidity * HUMIDITY_COFF;
        double score = finalScore(oxygenScore + humidityScore);
        return score - numberOfHikers * HIKERS_COFF;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("altitude", altitude);
        return objectNode;
    }
}
