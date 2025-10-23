package fileio;

import java.util.List;

public class TerritorySectionParamsInput {
    private List<SoilInput> soil;
    private List<PlantInput> plants;
    private List<AnimalInput> animals;
    private List<WaterInput> water;
    private List<AirInput> air;

    public final List<SoilInput> getSoil() {
        return soil;
    }

    public final List<AirInput> getAir() {
        return air;
    }

    public final List<AnimalInput> getAnimals() {
        return animals;
    }

    public final List<PlantInput> getPlants() {
        return plants;
    }

    public final List<WaterInput> getWater() {
        return water;
    }
}


