package fileio;

import lombok.Getter;

public class SimulationInput {
    @Getter private String territoryDim;
    @Getter private int energyPoints;
    @Getter private TerritorySectionParamsInput territorySectionParams;
}
