package my;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SimulationInput;
import fileio.TerritorySectionParamsInput;
import lombok.Getter;
import lombok.Setter;

public final class Simulation {
    @Getter @Setter private TerraBot terraBot;
    @Getter @Setter private TerritorySectionParams territorySectionParams;
    @Getter @Setter private Map map;
    @Getter @Setter private boolean started;
    @Getter @Setter private boolean ended;

    public Simulation() {
        started = false;
        ended = false;
    }
    public Simulation(final SimulationInput simulationInput) {
        this();
        String territoryDim = simulationInput.getTerritoryDim();
        String[] territoryDims = territoryDim.split("x");
        int height = Integer.parseInt(territoryDims[0]);
        int width = Integer.parseInt(territoryDims[1]);
        TerritorySectionParamsInput temp = simulationInput.getTerritorySectionParams();
        territorySectionParams = new TerritorySectionParams(temp);
        map = new Map(territorySectionParams, height, width);
        int energyPoints = simulationInput.getEnergyPoints();
        terraBot = new TerraBot(energyPoints);
        terraBot.setCell(map.getMap()[0][0]);
    }
    public ObjectNode printEnvConditions() {
        ObjectNode objectNode = terraBot.getCell().buildEnvConditions();
        return objectNode;
    }
    public ArrayNode printMap() {
        ArrayNode arrayNode = map.buildMapOutput();
        return arrayNode;
    }
}
