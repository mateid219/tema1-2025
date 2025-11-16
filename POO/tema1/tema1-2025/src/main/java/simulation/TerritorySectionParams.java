package simulation;

import entities.animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;
import entities.air.Air;
import lombok.Getter;

import java.util.ArrayList;

public final class TerritorySectionParams {
    @Getter private final ArrayList<Soil> soil;
    @Getter private final ArrayList<Air> air;
    @Getter private final ArrayList<Animal> animals;
    @Getter private final ArrayList<Plant> plants;
    @Getter private final ArrayList<Water> water;

    public TerritorySectionParams() {
        soil = new ArrayList<>();
        air = new ArrayList<>();
        animals = new ArrayList<>();
        plants = new ArrayList<>();
        water = new ArrayList<>();
    }

    public TerritorySectionParams(final Cell[][] map, final int height, final int width) {
        this();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (map[x][y].getAnimal() != null) {
                    animals.add(map[x][y].getAnimal());
                }
                if (map[x][y].getAir() != null) {
                    air.add(map[x][y].getAir());
                }
                if (map[x][y].getSoil() != null) {
                    soil.add(map[x][y].getSoil());
                }
                if (map[x][y].getWater() != null) {
                    water.add(map[x][y].getWater());
                }
                if (map[x][y].getPlant() != null) {
                    plants.add(map[x][y].getPlant());
                }
            }
        }


    }
}
