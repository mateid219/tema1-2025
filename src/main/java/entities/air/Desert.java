package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;

public final class Desert extends Air {
    private double dustParticles;
    @Getter @Setter private boolean desertStorm;

    private static final double MAX_SCORE = 65.0;
    private static final double OXYGEN_COEF = 2.0;
    private static final double TEMPERATURE_COEF = 0.3;
    private static final double DUST_COEF = 0.2;
    private static final double STORM_SCORE = 30.0;

    public Desert() { }
    public Desert(final AirInput airInput, int x, int y) {
        super(airInput, x , y);
        desertStorm = false;
        maxScore = MAX_SCORE;
        dustParticles = airInput.getDustParticles();
    }
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COEF;
        double temperatureScore = temperature * TEMPERATURE_COEF;
        double dustScore = dustParticles * DUST_COEF;
        double score = finalScore(oxygenScore - temperatureScore - dustScore);
        return score - (desertStorm ? STORM_SCORE : 0);
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        //objectNode.put("dustParticles", dustParticles);
        objectNode.put("desertStorm", desertStorm);
        return objectNode;
    }
}
