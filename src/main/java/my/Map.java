package my;

import entities.Air.Air;
import entities.Air.AirArrayList;
import entities.Animals.Animal;
import entities.Animals.AnimalArrayList;
import entities.Soil.Soil;
import entities.Soil.SoilArrayList;
import fileio.PairInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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
        SoilArrayList soilArrayList = territorySectionParams.getSoil();
        for (Soil soil : soilArrayList.getSoilArrayList()) {
            ArrayList<PairInput> sections = soil.getSections();
            for (PairInput section : sections) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setSoil(soil);
            }
        }
        AirArrayList airArrayList = territorySectionParams.getAir();
        for (Air air : airArrayList.getAirArrayList()) {
            ArrayList<PairInput> sections = air.getSections();
            for (PairInput section : sections) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setAir(air);
            }
        }

        AnimalArrayList animalArrayList = territorySectionParams.getAnimals();
        for (Animal animal : animalArrayList.getAnimalArrayList()) {
            ArrayList<PairInput> sections = animal.getSections();
            for (PairInput section : sections) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setAnimal(animal);
            }
        }
    }
}
