package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class ForestSoil extends Soil {
    @Getter @Setter private double leafLitter;

    private static final double NITROGEN_SCORE_COEF = 1.2;
    private static final double RETENTION_SCORE_COEF = 1.5;
    private static final double MATTER_SCORE_COEF = 2.0;
    private static final double LITTER_SCORE_COEF = 0.3;

    private static double RETENTION_STUCK_COEF = 0.6;
    private static double LITTER_STUCK_COEF = 0.4;
    private static double STUCK_PERCENTAGE = 80.0;


    public ForestSoil() { }
    public ForestSoil(final SoilInput soilInput, int x, int y) {
        super(soilInput, x, y);
        leafLitter = soilInput.getLeafLitter();
    }

    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'ForestSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COEF;
        double waterRetentionScore = waterRetention * RETENTION_SCORE_COEF;
        double organicMatterScore = organicMatter * MATTER_SCORE_COEF;
        double leafLitterScore = leafLitter * LITTER_SCORE_COEF;
        return nitrogenScore + organicMatterScore + waterRetentionScore + leafLitterScore;
    }
    public double possibilityToGetStuckInSoil() {
        return (waterRetention * RETENTION_STUCK_COEF + leafLitter * LITTER_STUCK_COEF)
                / STUCK_PERCENTAGE * MAX_PERCENTAGE;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("leafLitter", leafLitter);
        return objectNode;
    }
}
