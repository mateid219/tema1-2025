package fileio;

import lombok.Getter;

import java.util.List;

public final class AnimalInput implements InputEntity {
    @Getter private String type;
    @Getter private String name;
    @Getter private double mass;
    @Getter private List<PairInput> sections;
}

