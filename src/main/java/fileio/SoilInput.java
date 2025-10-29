package fileio;

import lombok.Getter;

import java.util.List;

public class SoilInput {
    @Getter private String type;
    @Getter private String name;
    @Getter private double mass;
    @Getter private double nitrogen;
    @Getter private double waterRetention;
    @Getter private double soilpH;
    @Getter private double organicMatter;
    @Getter private double leafLitter;
    @Getter private double waterLogging;
    @Getter private double permafrostDepth;
    @Getter private double rootDensity;
    @Getter private double salinity;
    @Getter private List<PairInput> sections;
}

