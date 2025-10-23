package fileio;

import java.util.List;

public class SoilInput {
    private String type;
    private String name;
    private double mass;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double leafLitter;
    private double waterLogging;
    private double permafrostDepth;
    private double rootDensity;
    private double salinity;
    private List<PairInput> sections;

    public final String getType() {
        return type;
    }

    public final String getName() {
        return name;
    }

    public final double getMass() {
        return mass;
    }

    public final double getNitrogen() {
        return nitrogen;
    }

    public final double getWaterRetention() {
        return waterRetention;
    }

    public final double getSoilpH() {
        return soilpH;
    }

    public final double getLeafLitter() {
        return leafLitter;
    }

    public final double getOrganicMatter() {
        return organicMatter;
    }

    public final double getPermafrostDepth() {
        return permafrostDepth;
    }

    public final double getRootDensity() {
        return rootDensity;
    }

    public final double getSalinity() {
        return salinity;
    }

    public final double getWaterLogging() {
        return waterLogging;
    }

    public final List<PairInput> getSections() {
        return sections;
    }
}

