package simulation.environmentMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.TerritorySectionParamsInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public final class EnvironmentMap {
    @Getter private final int height;
    @Getter private final int width;
    @Getter private final Cell[][] map;

    static final int NUM_DIR = 4;
    static final int[] VX = {0, 1, 0, -1};
    static final int[] VY = {1, 0, -1, 0};
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public EnvironmentMap(String territoryDim) {
        String[] territoryDims = territoryDim.split("x");
        height = Integer.parseInt(territoryDims[0]);
        width = Integer.parseInt(territoryDims[1]);
        map = new Cell[width][height];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                map[i][j] = new Cell(i, j);
            }
        }
    }
    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Cell[] line : map) {
            cells.addAll(Arrays.asList(line));
        }
        return cells;
    }
    /**
     * Creates distinct entities for every section and places them on the map.
     */
    public void placeEntities(final TerritorySectionParamsInput territorySectionParams) {
        EntityPlacer.placeSoil(territorySectionParams.getSoil(), map);
        EntityPlacer.placeAir(territorySectionParams.getAir(), map);
        EntityPlacer.placePlants(territorySectionParams.getPlants(), map);
        EntityPlacer.placeAnimals(territorySectionParams.getAnimals(), map);
        EntityPlacer.placeWater(territorySectionParams.getWater(), map);
    }
    /**
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return Cell at (x,y)
     */
    public Cell cellAt(final int x, final int y) {
        return map[x][y];
    }
    private boolean outsideMap(final int x, final int y) {
        return (x < 0 || x >= width || y < 0 || y >= height);
    }
    private ArrayList<Cell> findNeighbours(final Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int i = 0; i < NUM_DIR; i++) {
            int neighbourX = cell.getX() + VX[i];
            int neighbourY = cell.getY() + VY[i];
            if (outsideMap(neighbourX, neighbourY)) {
                continue;
            }
            Cell neighbour = cellAt(neighbourX, neighbourY);
            neighbours.add(neighbour);
        }
        return neighbours;
    }
    public Cell nextCell(final Cell cell, Comparator<Cell> preference) {
        ArrayList<Cell> neighbours = findNeighbours(cell);
        return Collections.max(neighbours, preference);
    }
    /**
     * Builds the arrayNode required for printMap
     * @return output for printMap command
     */
    public ArrayNode buildMapOutput() {
        ArrayNode arrayNode = MAPPER.createArrayNode();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                arrayNode.add(map[j][i].buildCellOutput());
            }
        }
        return arrayNode;
    }
}
