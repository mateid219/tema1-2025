package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class ForestSoil extends Soil {
    @Getter @Setter private double leafLitter;

    private static final double NITROGEN_COEF = 1.2;
    private static final double RETENTION_COEF = 2.0;
    private static final double MATTER_COEF = 2.0;
    private static final double LITTER_COEF = 0.3;


    public ForestSoil() { }
    public ForestSoil(final SoilInput soilInput) {
        super();
        leafLitter = soilInput.getLeafLitter();
    }

    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'ForestSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = getNitrogen() * NITROGEN_COEF;
        double waterRetentionScore = getWaterRetention() * RETENTION_COEF;
        double organicMatterScore = getOrganicMatter() * MATTER_COEF;
        double leafLitterScore = leafLitter * LITTER_COEF;
        return nitrogenScore + organicMatterScore + waterRetentionScore + leafLitterScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("leafLitter", leafLitter);
        return objectNode;
    }
}
