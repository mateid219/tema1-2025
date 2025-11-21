package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class SwampSoil extends Soil {
    @Getter @Setter private double waterLogging;

    private static final double NITROGEN_SCORE_COFF = 1.1;
    private static final double MATTER_SCORE_COFF = 2.2;
    private static final double WATER_SCORE_COFF = 5.0;

    private static final double LOGGING_STUCK_COFF = 10.0;

    public SwampSoil(final SoilInput soilInput) {
        super(soilInput);
        waterLogging = soilInput.getWaterLogging();
    }

    @Override
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COFF;
        double organicMatterScore = organicMatter * MATTER_SCORE_COFF;
        double waterLoggingScore = waterLogging * WATER_SCORE_COFF;
        return nitrogenScore + organicMatterScore - waterLoggingScore;
    }
    @Override
    public double possibilityToGetStuckInSoil() {
        return waterLogging * LOGGING_STUCK_COFF;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("waterLogging", waterLogging);
        return objectNode;
    }
}
