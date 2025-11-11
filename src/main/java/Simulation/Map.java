package Simulation;

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

import static Simulation.Cell.MAPPER;

public final class Map {
    @Getter @Setter private int height;
    @Getter @Setter private int width;
    @Getter @Setter private Cell[][] map;

    public Map() { }
    public Map(final int height, final int width) {
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
    private <T,I> void placeEntities(ArrayList<T> arrayList , BiConsumer<Cell, T> setter,
                                     Function<T, Integer> getX, Function<T, Integer> getY ) {
        for (T entity : arrayList) {
            int x = getX.apply(entity);
            int y = getY.apply(entity);
            setter.accept(map[x][y], entity);
        }
    }
    public Map(final TerritorySectionParams territorySectionParams,
               final int height, final int width) {

        this(height, width);
        placeEntities(territorySectionParams.getSoil(),
                Cell::setSoil, Soil::getX, Soil::getY);
        placeEntities(territorySectionParams.getAir(),
                Cell::setAir, Air::getX, Air::getY);
        placeEntities(territorySectionParams.getWater(),
                Cell::setWater, Water::getX, Water::getY);
        placeEntities(territorySectionParams.getAnimals(),
                Cell::setAnimal, Animal::getX, Animal::getY);
        placeEntities(territorySectionParams.getPlants(),
                Cell::setPlant, Plant::getX, Plant::getY);
    }
    public boolean insideMap(int x, int y) {
        return (0 <= x && x < width) &&
                (0 <= y && y < height);
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
