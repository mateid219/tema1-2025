package simulation;

import entities.Animals.Animal;
import entities.Soil.Soil;
import entities.Water.Water;
import entities.air.Air;
import entities.Plants.Plant;

import fileio.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class TerritorySectionParams {
    @Getter @Setter private ArrayList<Soil> soil;
    @Getter @Setter private ArrayList<Air> air;
    @Getter @Setter private ArrayList<Animal> animals;
    @Getter @Setter private ArrayList<Plant> plants;
    @Getter @Setter private ArrayList<Water> water;

    public TerritorySectionParams() { }

    public TerritorySectionParams(final TerritorySectionParamsInput territorySectionParamsInput) {
        soil = new ArrayList<>();
        for (SoilInput soilInput : territorySectionParamsInput.getSoil()) {
            for (PairInput pairInput : soilInput.getSections())
                soil.add(Soil.createSoil(soilInput, pairInput.getX(), pairInput.getY()));
        }
        air = new ArrayList<>();
        for (AirInput airInput : territorySectionParamsInput.getAir()) {
            for (PairInput pairInput : airInput.getSections())
                air.add(Air.createAir(airInput, pairInput.getX(), pairInput.getY()));
        }
        animals = new ArrayList<>();
        for (AnimalInput animalInput : territorySectionParamsInput.getAnimals()) {
            for (PairInput pairInput : animalInput.getSections())
                animals.add(Animal.createAnimal(animalInput, pairInput.getX(), pairInput.getY()));
        }
        plants = new ArrayList<>();
        for (PlantInput plantInput : territorySectionParamsInput.getPlants())
            for (PairInput pairInput : plantInput.getSections())
                plants.add(Plant.createPlant(plantInput, pairInput.getX(), pairInput.getY()));
        water = new ArrayList<>();
        for (WaterInput waterInput : territorySectionParamsInput.getWater())
            for (PairInput pairInput : waterInput.getSections())
                water.add(Water.createWater(waterInput, pairInput.getX(), pairInput.getY()));

    }
}
