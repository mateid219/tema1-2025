package simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.animals.CellPreferences;
import fileio.TerritorySectionParamsInput;
import lombok.Getter;
import lombok.Setter;
import simulation.environmentMap.EntityPlacer;

import java.util.ArrayList;


public final class EnvironmentMap {
    @Getter @Setter private int height;
    @Getter @Setter private int width;
    @Getter @Setter private Cell[][] map;

    static final int NUM_DIR = 4;
    static final int[] VX = {0, 1, 0, -1};
    static final int[] VY = {1, 0, -1, 0};
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public EnvironmentMap() { }
    public EnvironmentMap(final int height, final int width) {
        this.height = height;
        this.width = width;
        map = new Cell[height][];
        for (int i = 0; i < height; ++i) {
            map[i] = new Cell[width];
            for (int j = 0; j < width; ++j) {
                map[i][j] = new Cell(i, j);
            }
        }
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

    /**
     * Checks if given coordinates are outside the map.
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if (x,y) is outside the map
     */
    public boolean outsideMap(final int x, final int y) {
        return (x < 0 || x >= width || y < 0 || y >= height);
    }

    /**
     * Returns cell neighbours as an ArrayList.
     * @return {neighbour_1, neighbour2, ...}
     */
    public ArrayList<Cell> findNeighbours(final Cell cell) {
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

    /**
     * Decides the next cell for the robot to move,
     * based on cell order and quality.
     * @return the cell to which the robot should move.
     */
    public Cell robotNextCell(final Cell robotCell) {
        Cell nextCell = null;
        int bestQuality = -1;
        ArrayList<Cell> neighbours = findNeighbours(robotCell);
        for (Cell neighbour : neighbours) {
            int nextCellQuality = neighbour.calculateCellQuality();
            if (bestQuality == -1 || nextCellQuality < bestQuality) {
                bestQuality = nextCellQuality;
                nextCell = neighbour;
            }
        }
        return nextCell;
    }
    private static final String PARASITE = "Parasites";
    private static final String CARNIVORE = "Carnivores";

    /**
     * Decides the next cell for an animal to move,
     * based on cell order and cell content.
     * @param animalCell the cell on which the animal that moves resides
     * @param type the type of the animal
     * @return the cell to which the animal should move.
     */
    public Cell animalNextCell(final Cell animalCell, final String type) {
        CellPreferences nextCell = new CellPreferences();
        for (int dir = 0; dir < NUM_DIR; ++dir) {
            int newX = animalCell.getX() + VX[dir];
            int newY = animalCell.getY() + VY[dir];
            if (outsideMap(newX, newY)) {
                continue;
            }
            CellPreferences newCell = new CellPreferences(map[newX][newY]);
            if (newCell.getCell().getAnimal() != null
                    && !(PARASITE.equals(type) || CARNIVORE.equals(type))) {
                continue;
            }
            if (nextCell.compareTo(newCell) < 0) {
                nextCell = newCell;
            }
        }
        return nextCell.getCell();
    }

    /**
     * Builds the arrayNode required for printMap
     * @return output for printMap command
     */
    public ArrayNode buildMapOutput() {
        ArrayNode arrayNode = MAPPER.createArrayNode();
        for (int j = 0; j < width; ++j) {
            for (int i = 0; i < height; ++i) {
                arrayNode.add(map[i][j].buildCellOutput());
            }
        }
        return arrayNode;
    }
}
