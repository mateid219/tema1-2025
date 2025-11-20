package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class ForestSoil extends Soil {
    @Getter @Setter private double leafLitter;

    private static final double NITROGEN_SCORE_COFF = 1.2;
    private static final double RETENTION_SCORE_COFF = 1.5;
    private static final double MATTER_SCORE_COFF = 2.0;
    private static final double LITTER_SCORE_COFF = 0.3;

    private static final double RETENTION_STUCK_COFF = 0.6;
    private static final double LITTER_STUCK_COFF = 0.4;
    private static final double STUCK_PERCENTAGE = 80.0;


    public ForestSoil() { }
    public ForestSoil(final SoilInput soilInput) {
        super(soilInput);
        leafLitter = soilInput.getLeafLitter();
    }

    @Override
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COFF;
        double waterRetentionScore = waterRetention * RETENTION_SCORE_COFF;
        double organicMatterScore = organicMatter * MATTER_SCORE_COFF;
        double leafLitterScore = leafLitter * LITTER_SCORE_COFF;
        return nitrogenScore + organicMatterScore + waterRetentionScore + leafLitterScore;
    }
    @Override
    public double possibilityToGetStuckInSoil() {
        return (waterRetention * RETENTION_STUCK_COFF + leafLitter * LITTER_STUCK_COFF)
                / STUCK_PERCENTAGE * MAX_PERCENTAGE;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("leafLitter", leafLitter);
        return objectNode;
    }
}
