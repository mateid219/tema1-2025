package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class TundraSoil extends Soil {
    @Getter @Setter private double permafrostDepth;

    private static final double NITROGEN_COEF = 0.7;
    private static final double MATTER_COEF = 0.5;
    private static final double PERMAFROST_COEF = 1.5;

    public TundraSoil() { }
    public TundraSoil(final SoilInput soilInput) {
        super(soilInput);
        permafrostDepth = soilInput.getPermafrostDepth();
    }

    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii solului de tip 'TundraSoil'
     * @return score
     */
    public double calculateScore() {
        double nitrogenScore = getNitrogen() * NITROGEN_COEF;
        double organicMatterScore = getOrganicMatter() * MATTER_COEF;
        double permafrostDepthScore = permafrostDepth * PERMAFROST_COEF;
        return nitrogenScore + organicMatterScore - permafrostDepthScore;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("permafrostDepth", permafrostDepth);
        return objectNode;
    }
}
