package entities.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;

public final class Temperate extends Air {
    private double pollenLevel;

    private static final double MAX_SCORE = 84.0;
    private static final double OXYGEN_COEF = 2.0;
    private static final double HUMIDITY_COEF = 0.7;
    private static final double POLLEN_COEF = 0.1;

    public Temperate() { }
    public Temperate(final AirInput airInput) {
        super(airInput);
        maxScore = MAX_SCORE;
        pollenLevel = airInput.getPollenLevel();
    }
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COEF;
        double humidityScore = humidity * HUMIDITY_COEF;
        double pollenScore = pollenLevel * POLLEN_COEF;
        return oxygenScore + humidityScore - pollenScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("pollenLevel", pollenLevel);
        return objectNode;
    }
}
