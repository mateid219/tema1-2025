package my_classes;

import fileio.SimulationInput;
import lombok.Getter;
import lombok.Setter;

public class Simulation {
    @Getter @Setter private int height;
    @Getter @Setter private int width;
    @Getter @Setter private int energyPoints;

    public Simulation(SimulationInput simulationInput) {
        String territoryDim = simulationInput.getTerritoryDim();
        String[] territoryDims = territoryDim.split("x");
        height = Integer.parseInt(territoryDims[0]);
        width = Integer.parseInt(territoryDims[1]);
        energyPoints = simulationInput.getEnergyPoints();

    }
}
