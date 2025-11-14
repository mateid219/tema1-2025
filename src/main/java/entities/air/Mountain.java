package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;

public final class Mountain extends Air {
    private double altitude;
    @Setter @Getter
    private int numberOfHikers;

    private static final double MAX_SCORE = 78.0;
    private static final double OXYGEN_COEF = 2.0;
    private static final double ALTITUDE_COEF = 0.0005;
    private static final double HUMIDITY_COEF = 0.6;
    private static final double HIKERS_COEF = 0.6;

    public Mountain() { }
    public Mountain(final AirInput airInput, int x, int y) {
        super(airInput, x , y);
        numberOfHikers = 0;
        maxScore = MAX_SCORE;
        altitude = airInput.getAltitude();
    }
    public double calculateScore() {
        double oxygenFactor = oxygenLevel - altitude * ALTITUDE_COEF;
        double oxygenScore = oxygenFactor * OXYGEN_COEF;
        double humidityScore = humidity * HUMIDITY_COEF;
        double score = finalScore(oxygenScore + humidityScore);
        return score - numberOfHikers * HIKERS_COEF;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("altitude", altitude);
        return objectNode;
    }
}
