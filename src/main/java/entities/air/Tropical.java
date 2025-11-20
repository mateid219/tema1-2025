package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Setter;

public final class Tropical extends Air {
    private double co2Level;
    @Setter private double rainfall;

    private static final double MAX_SCORE = 82.0;
    private static final double OXYGEN_COFF = 2.0;
    private static final double HUMIDITY_COFF = 0.5;
    private static final double CO2LEVEL_COFF = 0.01;
    private static final double RAINFALL_COFF = 0.3;

    public Tropical() { }
    public Tropical(final AirInput airInput) {
        super(airInput);
        maxScore = MAX_SCORE;
        co2Level = airInput.getCo2Level();
    }
    @Override
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COFF;
        double humidityScore = humidity * HUMIDITY_COFF;
        double co2LevelScore = co2Level * CO2LEVEL_COFF;
        double score = finalScore(oxygenScore + humidityScore - co2LevelScore);
        return score + rainfall * RAINFALL_COFF;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        co2Level = Math.round(co2Level * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        objectNode.put("co2Level", co2Level);
        return objectNode;
    }
}
