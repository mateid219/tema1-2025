package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Setter;

public final class Tropical extends Air {
    private double co2Level;
    @Setter private double rainfall;

    private static final double MAX_SCORE = 82.0;
    private static final double OXYGEN_COEF = 2.0;
    private static final double HUMIDITY_COEF = 0.5;
    private static final double CO2LEVEL_COEF = 0.01;
    private static final double RAINFALL_COEF = 0.3;

    public Tropical() { }
    public Tropical(final AirInput airInput, int x, int y) {
        super(airInput, x , y);
        maxScore = MAX_SCORE;
        co2Level = airInput.getCo2Level();
    }
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COEF;
        double humidityScore = humidity * HUMIDITY_COEF;
        double co2LevelScore = co2Level * CO2LEVEL_COEF;
        return oxygenScore + humidityScore - co2LevelScore - rainfall * RAINFALL_COEF;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("co2Level", co2Level);
        return objectNode;
    }
}
