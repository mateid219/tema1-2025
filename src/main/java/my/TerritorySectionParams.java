package my;

import entities.Air.AirArrayList;
import entities.Animals.AnimalArrayList;
import entities.Plants.PlantArrayList;
import entities.Soil.SoilArrayList;

import fileio.AirInput;
import fileio.AnimalInput;
import fileio.SoilInput;
import fileio.TerritorySectionParamsInput;
import fileio.PlantInput;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TerritorySectionParams {
    @Getter @Setter private SoilArrayList soil;
    @Getter @Setter private AirArrayList air;
    @Getter @Setter private AnimalArrayList animals;
    @Getter @Setter private PlantArrayList plants;

    public TerritorySectionParams() { }
    public TerritorySectionParams(final TerritorySectionParamsInput territorySectionParamsInput) {
        List<SoilInput> soilInputList = territorySectionParamsInput.getSoil();
        ArrayList<SoilInput> soilInputArrayList = new ArrayList<>(soilInputList);
        soil = new SoilArrayList(soilInputArrayList);

        List<AirInput> airInputList = territorySectionParamsInput.getAir();
        ArrayList<AirInput> airInputArrayList = new ArrayList<>(airInputList);
        air = new AirArrayList(airInputArrayList);

        List<AnimalInput> animalInputList = territorySectionParamsInput.getAnimals();
        ArrayList<AnimalInput> animalInputArrayList = new ArrayList<>(animalInputList);
        animals = new AnimalArrayList(animalInputArrayList);

        List<PlantInput> plantInputList = territorySectionParamsInput.getPlants();
        ArrayList<PlantInput> plantInputArrayList = new ArrayList<>(plantInputList);
        plants = new PlantArrayList(plantInputArrayList);
    }
}
