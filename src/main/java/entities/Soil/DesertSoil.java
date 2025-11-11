package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

import javax.print.attribute.standard.MediaSize;

public final class DesertSoil extends Soil {
    @Getter @Setter private double salinity;

    private static final double NITROGEN_SCORE_COEF = 0.5;
    private static final double RETENTION_SCORE_COEF = 0.3;
    private static final double SALINITY_SCORE_COEF = 2.0;


    public DesertSoil() { }
    public DesertSoil(final SoilInput soilInput, int x, int y) {
        super(soilInput, x, y);
        salinity = soilInput.getSalinity();
    }
    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'DesertSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COEF;
        double waterRetentionScore = waterRetention * RETENTION_SCORE_COEF;
        double salinityScore = salinity * SALINITY_SCORE_COEF;
        return nitrogenScore + waterRetentionScore - salinityScore;
    }
    public double possibilityToGetStuckInSoil() {
        return (100.0 - waterRetention + salinity) / MAX_PERCENTAGE * MAX_PERCENTAGE;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("salinity", salinity);
        return objectNode;
    }
}
