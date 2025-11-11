package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class SwampSoil extends Soil {
    @Getter @Setter private double waterLogging;

    private static final double NITROGEN_SCORE_COEF = 1.1;
    private static final double MATTER_SCORE_COEF = 2.2;
    private static final double WATER_SCORE_COEF = 5.0;

    private static final double LOGGING_STUCK_COEF = 10.0;

    public SwampSoil() { }
    public SwampSoil(final SoilInput soilInput, int x, int y) {
        super(soilInput, x, y);
        waterLogging = soilInput.getWaterLogging();
    }

    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'SwampSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COEF;
        double organicMatterScore = organicMatter * MATTER_SCORE_COEF;
        double waterLoggingScore = waterLogging * WATER_SCORE_COEF;
        return nitrogenScore + organicMatterScore - waterLoggingScore;
    }
    public double possibilityToGetStuckInSoil() {
        return waterLogging * LOGGING_STUCK_COEF;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("waterLogging", waterLogging);
        return objectNode;
    }
}
