package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class DesertSoil extends Soil {
    @Getter @Setter private double salinity;

    private static final double NITROGEN_COEF = 0.5;
    private static final double RETENTION_COEF = 0.3;
    private static final double SALINITY_COEF = 2.0;

    public DesertSoil() { }
    public DesertSoil(final SoilInput soilInput) {
        super(soilInput);
        salinity = soilInput.getSalinity();
    }

    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'DesertSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_COEF;
        double waterRetentionScore = waterRetention * RETENTION_COEF;
        double salinityScore = salinity * SALINITY_COEF;
        return nitrogenScore + waterRetentionScore - salinityScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("salinity", salinity);
        return objectNode;
    }
}
