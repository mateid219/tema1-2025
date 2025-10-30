package my;

import entities.Air.AirArrayList;
import entities.Animals.AnimalArrayList;
import entities.Plants.PlantArrayList;
import entities.Soil.SoilArrayList;

import entities.Water.WaterArrayList;
import fileio.TerritorySectionParamsInput;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TerritorySectionParams {
    @Getter @Setter private SoilArrayList soil;
    @Getter @Setter private AirArrayList air;
    @Getter @Setter private AnimalArrayList animals;
    @Getter @Setter private PlantArrayList plants;
    @Getter @Setter private WaterArrayList water;

    private static <T, R> R createTypedArrayList(
            final List<T> inputList, final Function<ArrayList<T>, R> constructor) {
        ArrayList<T> arrayList = new ArrayList<>(inputList);
        return constructor.apply(arrayList);
    }
    public TerritorySectionParams() { }

    public TerritorySectionParams(final TerritorySectionParamsInput territorySectionParamsInput) {
        soil = createTypedArrayList(
                territorySectionParamsInput.getSoil(),
                SoilArrayList::new);
        air = createTypedArrayList(
                territorySectionParamsInput.getAir(),
                AirArrayList::new);
        animals = createTypedArrayList(
                territorySectionParamsInput.getAnimals(),
                AnimalArrayList::new);
        plants = createTypedArrayList(
                territorySectionParamsInput.getPlants(),
                PlantArrayList::new);
        water = createTypedArrayList(
                territorySectionParamsInput.getWater(),
                WaterArrayList::new);
    }
}
