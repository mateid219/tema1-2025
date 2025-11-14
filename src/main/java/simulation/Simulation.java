package simulation;

import simulation.events.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SimulationInput;
import fileio.TerritorySectionParamsInput;
import lombok.Getter;
import lombok.Setter;
import simulation.terrabot.TerraBot;

import java.util.PriorityQueue;
import java.util.Queue;

import static simulation.EnvironmentMap.*;

public final class Simulation {

    @Getter private TerritorySectionParams territorySectionParams;
    @Getter private EnvironmentMap environmentMap;
    @Getter @Setter private boolean started;
    @Getter @Setter private boolean ended;
    @Getter private final Queue<Event> eventQueue;
    @Getter private TerraBot terraBot;

    public static ObjectMapper MAPPER = new ObjectMapper();

    private static final String PARASITE = "Parasites";
    private static final String CARNIVORE = "Carnivores";

    public Simulation() {
        started = false;
        ended = false;
        eventQueue = new PriorityQueue<Event>();
        terraBot = new TerraBot();
    }
    public Simulation(final SimulationInput simulationInput) {
        this();
        String territoryDim = simulationInput.getTerritoryDim();
        String[] territoryDims = territoryDim.split("x");
        int height = Integer.parseInt(territoryDims[0]);
        int width = Integer.parseInt(territoryDims[1]);
        TerritorySectionParamsInput temp = simulationInput.getTerritorySectionParams();
        territorySectionParams = new TerritorySectionParams(temp);
        environmentMap = new EnvironmentMap(territorySectionParams, height, width);
        int energyPoints = simulationInput.getEnergyPoints();
        terraBot = new TerraBot(energyPoints, environmentMap.cellAt(0, 0));
    }
    public void update(int timestamp) {
        while (!eventQueue.isEmpty() && eventQueue.peek().getTimestamp() <= timestamp) {
            Event event = eventQueue.poll();
            event.takeEffect(this);
        }
    }

    protected static class Solution implements Comparable<Solution> {
        Cell cell;
        double waterQuality;
        boolean hasPlant;
        boolean hasWater;

        public Solution() {
            cell = null;
            waterQuality = 0.0;
            hasWater = false;
            hasPlant = false;
        }
        public Solution(Cell cell) {
            this();
            this.cell = cell;
            hasWater = cell.getWater() != null && cell.getWater().isScanned();
            if (hasWater) {
                waterQuality = cell.getWater().calculateFinalScore();
            }
            hasPlant = cell.getPlant() != null && cell.getPlant().isScanned();
        }
        public int compareTo(Solution o) {
            if (hasWater && hasPlant && o.hasPlant && o.hasWater) {
                return (int) (waterQuality - o.waterQuality);
            }
            if (hasWater && hasPlant) {
                return 1;
            }
            if (o.hasWater && o.hasPlant) {
                return -1;
            }
            if (hasPlant && o.hasPlant) {
                return 0;
            }
            if (hasPlant) {
                return 1;
            }
            if (o.hasPlant) {
                return -1;
            }
            if (hasWater && o.hasWater) {
                return (int) (waterQuality - o.waterQuality);
            }
            if (hasWater) {
                return 1;
            }
            if (o.hasWater) {
                return -1;
            }
            if (cell == null) {
                return -1;
            }
            if (o.cell == null) {
                return 1;
            }
            return 0;
        }
    }
    public Cell animalNextCell(Cell animalCell, final String type) {
        Solution nextCell = new Solution();
        for (int dir = 0; dir < NUM_DIR; ++dir) {
            int newX = animalCell.getX() + vx[dir];
            int newY = animalCell.getY() + vy[dir];
            if (environmentMap.outsideMap(newX, newY)) {
                continue;
            }
            Solution newCell = new Solution(environmentMap.getMap()[newX][newY]);
            if (newCell.cell.getAnimal() != null && !(PARASITE.equals(type) || CARNIVORE.equals(type))) {
                continue;
            }
            if (nextCell.compareTo(newCell) < 0) {
                nextCell = newCell;
            }
        }
        if (nextCell.cell == null) {
            System.out.println("HERE");
            return null;
        }
        nextCell.cell.solution = nextCell;
        return nextCell.cell;
    }
    public ObjectNode printEnvConditions() {
        ObjectNode objectNode = terraBot.getCell().buildEnvConditions();
        return objectNode;
    }
    public ArrayNode printMap() {
        ArrayNode arrayNode = environmentMap.buildMapOutput();
        return arrayNode;
    }

}
