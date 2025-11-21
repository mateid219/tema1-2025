package fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class PlantInput implements EntityInput {
    private String type;
    private String name;
    private double mass;
    private List<PairInput> sections;
}

