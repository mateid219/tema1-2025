package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Setter;

public final class Temperate extends Air {
    private double pollenLevel;
    @Setter private String newSeason;

    private static final String SPRING = "Spring";
    private static final double MAX_SCORE = 84.0;
    private static final double OXYGEN_COFF = 2.0;
    private static final double HUMIDITY_COFF = 0.7;
    private static final double POLLEN_COFF = 0.1;
    private static final double NEW_SEASON_MODIFIER = 15.0;

    public Temperate() { }
    public Temperate(final AirInput airInput) {
        super(airInput);
        maxScore = MAX_SCORE;
        pollenLevel = airInput.getPollenLevel();
    }
    @Override
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COFF;
        double humidityScore = humidity * HUMIDITY_COFF;
        double pollenScore = pollenLevel * POLLEN_COFF;
        double score = finalScore(oxygenScore + humidityScore - pollenScore);
        return score - (SPRING.equalsIgnoreCase(newSeason) ? NEW_SEASON_MODIFIER : 0.0);
    }

    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("pollenLevel", pollenLevel);
        return objectNode;
    }
}
