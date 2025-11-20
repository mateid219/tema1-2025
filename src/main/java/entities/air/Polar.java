package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Setter;

public final class Polar extends Air {
    private double iceCrystalConcentration;
    @Setter private double windspeed;

    private static final double MAX_SCORE = 142.0;
    private static final double OXYGEN_COFF = 2.0;
    private static final double CRYSTAL_COFF = 0.05;
    private static final double WINDSPEED_COFF = 0.2;

    public Polar() { }
    public Polar(final AirInput airInput) {
        super(airInput);
        maxScore = MAX_SCORE;
        iceCrystalConcentration = airInput.getIceCrystalConcentration();
    }
    @Override
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COFF;
        double temperatureScore = MAX_SCORE - Math.abs(temperature);
        double crystalScore = iceCrystalConcentration * CRYSTAL_COFF;
        double score = finalScore(oxygenScore + temperatureScore - crystalScore);
        return score - windspeed * WINDSPEED_COFF;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("iceCrystalConcentration", iceCrystalConcentration);
        return objectNode;
    }
}
