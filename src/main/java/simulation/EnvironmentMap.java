package simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.air.Air;
import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static simulation.Simulation.MAPPER;

public final class EnvironmentMap {
    @Getter @Setter private int height;
    @Getter @Setter private int width;
    @Getter @Setter private Cell[][] map;

    static final int NUM_DIR = 4;
    static final int[] vx = {0, 1, 0, -1};
    static final int[] vy = {1, 0, -1, 0};

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
    private <T,I> void PlaceEntities(ArrayList<T> arrayList , BiConsumer<Cell, T> setter,
                                     Function<T, Integer> getX, Function<T, Integer> getY ) {
        for (T entity : arrayList) {
            int x = getX.apply(entity);
            int y = getY.apply(entity);
            setter.accept(map[x][y], entity);
        }
    }
    public EnvironmentMap(final TerritorySectionParams territorySectionParams,
                          final int height, final int width) {

        this(height, width);

        PlaceEntities(territorySectionParams.getSoil(),
                Cell::setSoil, Soil::getX, Soil::getY);
        PlaceEntities(territorySectionParams.getAir(),
                Cell::setAir, Air::getX, Air::getY);
        PlaceEntities(territorySectionParams.getWater(),
                Cell::setWater, Water::getX, Water::getY);
        PlaceEntities(territorySectionParams.getAnimals(),
                Cell::setAnimal, Animal::getX, Animal::getY);
        PlaceEntities(territorySectionParams.getPlants(),
                Cell::setPlant, Plant::getX, Plant::getY);
    }
    public Cell cellAt(int x, int y) {
        return map[x][y];
    }
    public boolean outsideMap(int x, int y) {
        return (x < 0 || x >= width || y < 0 || y >= height);
    }
    public ArrayList<Cell> findNeighbours(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int i = 0 ; i < NUM_DIR ; i++) {
            int neighbourX = cell.getX() + vx[i];
            int neighbourY = cell.getY() + vy[i];
            if (outsideMap(neighbourX, neighbourY)) {
                continue;
            }
            Cell neighbour = cellAt(neighbourX, neighbourY);
            neighbours.add(neighbour);
        }
        return neighbours;
    }
    public Cell robotNextCell(Cell robotCell) {
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
