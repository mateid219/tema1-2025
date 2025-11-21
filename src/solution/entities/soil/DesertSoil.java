package entities.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class DesertSoil extends Soil {
    @Getter @Setter private double salinity;

    private static final double NITROGEN_SCORE_COFF = 0.5;
    private static final double RETENTION_SCORE_COFF = 0.3;
    private static final double SALINITY_SCORE_COFF = 2.0;

    public DesertSoil(final SoilInput soilInput) {
        super(soilInput);
        salinity = soilInput.getSalinity();
    }
    @Override
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COFF;
        double waterRetentionScore = waterRetention * RETENTION_SCORE_COFF;
        double salinityScore = salinity * SALINITY_SCORE_COFF;
        return nitrogenScore + waterRetentionScore - salinityScore;
    }
    @Override
    public double possibilityToGetStuckInSoil() {
        return (MAX_PERCENTAGE - waterRetention + salinity) / MAX_PERCENTAGE * MAX_PERCENTAGE;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("salinity", salinity);
        return objectNode;
    }
}
