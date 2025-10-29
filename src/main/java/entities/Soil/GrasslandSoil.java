package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class GrasslandSoil extends Soil {
    @Getter @Setter private double rootDensity;

    private static final double NITROGEN_COEF = 1.3;
    private static final double MATTER_COEF = 1.5;
    private static final double DENSITY_COEF = 0.8;


    public GrasslandSoil() { }
    public GrasslandSoil(final SoilInput soilInput) {
        super(soilInput);
        rootDensity = soilInput.getRootDensity();
    }

    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'GrasslandSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_COEF;
        double organicMatterScore = organicMatter * MATTER_COEF;
        double rootDensityScore = rootDensity * DENSITY_COEF;
        return nitrogenScore + organicMatterScore + rootDensityScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("rootDensity", rootDensity);
        return objectNode;
    }
}
