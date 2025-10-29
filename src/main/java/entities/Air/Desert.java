package entities.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;

public final class Desert extends Air {
    private double dustParticles;

    private static final double MAX_SCORE = 65.0;
    private static final double OXYGEN_COEF = 2.0;
    private static final double TEMPERATURE_COEF = 0.3;
    private static final double DUST_COEF = 0.2;

    public Desert() { }
    public Desert(final AirInput airInput) {
        super(airInput);
        maxScore = MAX_SCORE;
        dustParticles = airInput.getDustParticles();
    }
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COEF;
        double temperatureScore = temperature * TEMPERATURE_COEF;
        double dustScore = dustParticles * DUST_COEF;
        return oxygenScore - temperatureScore - dustScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("dustParticles", dustParticles);
        return objectNode;
    }
}
