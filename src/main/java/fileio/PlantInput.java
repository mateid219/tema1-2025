package fileio;

import lombok.Getter;

import java.util.List;

public class PlantInput {
    @Getter private String type;
    @Getter private String name;
    @Getter private double mass;
    @Getter private List<PairInput> sections;
}

