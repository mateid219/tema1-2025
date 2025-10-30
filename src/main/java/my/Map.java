package my;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air.Air;
import entities.Air.AirArrayList;
import entities.Animals.Animal;
import entities.Animals.AnimalArrayList;
import entities.Plants.Plant;
import entities.Plants.PlantArrayList;
import entities.Soil.Soil;
import entities.Soil.SoilArrayList;
import entities.Water.Water;
import entities.Water.WaterArrayList;
import fileio.PairInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static my.Cell.MAPPER;

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
    public Map(final TerritorySectionParams territorySectionParams,
               final int height, final int width) {

        this(height, width);
        placeTypedEntities(territorySectionParams.getSoil(),
                SoilArrayList::getSoilArrayList,
                Soil::getSections, Cell::setSoil);
        placeTypedEntities(territorySectionParams.getAir(),
                AirArrayList::getAirArrayList,
                Air::getSections, Cell::setAir);
        placeTypedEntities(territorySectionParams.getAnimals(),
                AnimalArrayList::getAnimalArrayList,
                Animal::getSections, Cell::setAnimal);
        placeTypedEntities(territorySectionParams.getPlants(),
                PlantArrayList::getPlantArrayList,
                Plant::getSections, Cell::setPlant);
        placeTypedEntities(territorySectionParams.getWater(),
                WaterArrayList::getWaterArrayList,
                Water::getSections, Cell::setWater);
    }
    private <L, T> void placeTypedEntities(L arrayList , Function<L, ArrayList<T>> listExtractor,
                                      Function<T, ArrayList<PairInput>> sectionGetter,
                                           BiConsumer<Cell, T> setter) {
        ArrayList<T> typedArrayList = listExtractor.apply(arrayList);
        for (T typedEntity : typedArrayList) {
            ArrayList<PairInput> sections = sectionGetter.apply(typedEntity);
            for (PairInput section : sections) {
                int x = section.getX();
                int y = section.getY();
                setter.accept(map[x][y], typedEntity);
            }

        }
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
