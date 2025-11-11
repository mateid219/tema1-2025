package Simulation;

import Events.Event;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SimulationInput;
import fileio.TerritorySectionParamsInput;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public final class Simulation {
    @Getter @Setter private TerraBot terraBot;
    @Getter @Setter private TerritorySectionParams territorySectionParams;
    @Getter @Setter private Map map;
    @Getter @Setter private boolean started;
    @Getter @Setter private boolean ended;
    @Getter @Setter private Queue<Event> eventQueue;

    static int NUM_DIR = 4;
    static int[] vx = {0, 1, 0, -1};
    static int[] vy = {1, 0, -1, 0};

    public Simulation() {
        started = false;
        ended = false;
        eventQueue = new PriorityQueue<Event>();
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
    public Cell robotNextCell() {
        Cell robotCell = terraBot.getCell();
        Cell nextCell = null;
        int bestQuality = -1;
        for (int dir = 0; dir < NUM_DIR; ++dir) {
            int newX = robotCell.getX() + vx[dir];
            int newY = robotCell.getY() + vy[dir];
            if (! map.insideMap(newX, newY)) {
                continue;
            }
            Cell newCell = map.getMap()[newX][newY];
            int nextCellQuality = newCell.calculateCellQuality();
            System.out.printf("Cell (%d,%d) has QT = %d ", newX, newY, nextCellQuality);
            System.out.print(newCell.dbgQuality2());
            System.out.println(newCell.buildEnvConditions());
            if (bestQuality == -1 || nextCellQuality < bestQuality) {
                bestQuality = nextCellQuality;
                nextCell = newCell;
            }
        }
        return nextCell;
    }
    private class Solution implements Comparable<Solution> {
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
    public Cell animalNextCell(Cell animalCell) {
        Solution nextCell = new Solution();
        for (int dir = 0; dir < NUM_DIR; ++dir) {
            int newX = animalCell.getX() + vx[dir];
            int newY = animalCell.getY() + vy[dir];
            if (!map.insideMap(newX, newY)) {
                continue;
            }
            Solution newCell = new Solution(map.getMap()[newX][newY]);
            if (nextCell.compareTo(newCell) < 0) {
                nextCell = newCell;
            }
        }
        if (nextCell.cell == null) {
            System.out.println("HERE");
        }
        return nextCell.cell;
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
