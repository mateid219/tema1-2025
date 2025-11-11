package fileio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WaterInput {
    private String type;
    private String name;
    private double mass;
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    @JsonProperty("pH")
    private double pH;
    @JsonProperty("isFrozen")
    private boolean isFrozen;
    private List<PairInput> sections;
}

