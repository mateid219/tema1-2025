package entities.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;

public final class Polar extends Air {
    private double iceCrystalConcentration;

    private static final double MAX_SCORE = 142.0;
    private static final double OXYGEN_COEF = 2.0;
    private static final double CRYSTAL_COEF = 0.05;

    public Polar() { }
    public Polar(final AirInput airInput) {
        super(airInput);
        maxScore = MAX_SCORE;
        iceCrystalConcentration = airInput.getIceCrystalConcentration();
    }
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COEF;
        double temperatureScore = MAX_SCORE - Math.abs(temperature);
        double crystalScore = iceCrystalConcentration * CRYSTAL_COEF;
        return oxygenScore + temperatureScore - crystalScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("iceCrystalConcentration", iceCrystalConcentration);
        return objectNode;
    }
}
