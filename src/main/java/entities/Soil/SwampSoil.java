package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class SwampSoil extends Soil {
    @Getter @Setter private double waterLogging;

    private static final double NITROGEN_COEF = 1.1;
    private static final double MATTER_COEF = 2.2;
    private static final double WATER_COEF = 5.0;

    public SwampSoil() { }
    public SwampSoil(final SoilInput soilInput) {
        super(soilInput);
        waterLogging = soilInput.getWaterLogging();
    }

    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'SwampSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = getNitrogen() * NITROGEN_COEF;
        double organicMatterScore = getOrganicMatter() * MATTER_COEF;
        double waterLoggingScore = waterLogging * WATER_COEF;
        return nitrogenScore + organicMatterScore - waterLoggingScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("waterLogging", waterLogging);
        return objectNode;
    }
}
