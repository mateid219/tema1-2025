package fileio;

public class SimulationInput {
    private String territoryDim;
    private int energyPoints;
    private TerritorySectionParamsInput territorySectionParams;

    public final String getTerritoryDim() {
        return territoryDim;
    }

    public final int getEnergyPoints() {
        return energyPoints;
    }

    public final TerritorySectionParamsInput getTerritorySectionParams() {
        return territorySectionParams;
    }
}
