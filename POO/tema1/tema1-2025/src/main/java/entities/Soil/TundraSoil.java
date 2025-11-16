package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

public final class TundraSoil extends Soil {
    @Getter @Setter private double permafrostDepth;

    private static final double NITROGEN_SCORE_COEF = 0.7;
    private static final double MATTER_SCORE_COEF = 0.5;
    private static final double PERMAFROST_SCORE_COEF = 1.5;


    private static final double PERMAFROST_STUCK_COMPLEMENT = 50.0;
    private static final double STUCK_PERCENTAGE = 50.0;

    public TundraSoil() { }
    public TundraSoil(final SoilInput soilInput) {
        super(soilInput);
        permafrostDepth = soilInput.getPermafrostDepth();
    }

    @Override
    public double calculateScore() {
        double nitrogenScore = nitrogen * NITROGEN_SCORE_COEF;
        double organicMatterScore = organicMatter * MATTER_SCORE_COEF;
        double permafrostDepthScore = permafrostDepth * PERMAFROST_SCORE_COEF;
        return nitrogenScore + organicMatterScore - permafrostDepthScore;
    }
    @Override
    public double possibilityToGetStuckInSoil() {
        return (PERMAFROST_STUCK_COMPLEMENT - permafrostDepth)
                / STUCK_PERCENTAGE * MAX_PERCENTAGE;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("permafrostDepth", permafrostDepth);
        return objectNode;
    }
}
