package fileio;

import java.util.List;

public class WaterInput {
    private String type;
    private String name;
    private double mass;
    private double purity;
    private double salinity;
    private int turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;
    private List<PairInput> sections;

    public final List<PairInput> getSections() {
        return sections;
    }

    public final double getSalinity() {
        return salinity;
    }

    public final double getMass() {
        return mass;
    }

    public final String getName() {
        return name;
    }

    public final String getType() {
        return type;
    }

    public final double getContaminantIndex() {
        return contaminantIndex;
    }

    public final double getpH() {
        return pH;
    }

    public final double getPurity() {
        return purity;
    }

    public final int getTurbidity() {
        return turbidity;
    }
    public final boolean getIsFrozen() {
        return isFrozen;
    }
}

