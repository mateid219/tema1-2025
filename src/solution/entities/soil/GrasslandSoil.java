package entities.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class GrasslandSoil extends Soil {
    @Getter @Setter private double rootDensity;

    private static final double NITROGEN_SCORE_COFF = 1.3;
    private static final double MATTER_SCORE_COFF = 1.5;
    private static final double DENSITY_SCORE_COFF = 0.8;

    private static final double RETENTION_STUCK_COFF = 0.5;
    private static final double DENSITY_COMPLEMENT = 50.0;
    private static final double STUCK_PERCENTAGE = 75.0;

    public GrasslandSoil(final SoilInput soilInput) {
        super(soilInput);
        rootDensity = soilInput.getRootDensity();
    }

    @Override
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COFF;
        double organicMatterScore = organicMatter * MATTER_SCORE_COFF;
        double rootDensityScore = rootDensity * DENSITY_SCORE_COFF;
        return nitrogenScore + organicMatterScore + rootDensityScore;
    }
    @Override
    public double possibilityToGetStuckInSoil() {
        return (DENSITY_COMPLEMENT - rootDensity + waterRetention * RETENTION_STUCK_COFF)
                / STUCK_PERCENTAGE * MAX_PERCENTAGE;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("rootDensity", rootDensity);
        return objectNode;
    }
}
