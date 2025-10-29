package fileio;

import lombok.Getter;

import java.util.List;

public class TerritorySectionParamsInput {
    @Getter private List<SoilInput> soil;
    @Getter private List<PlantInput> plants;
    @Getter private List<AnimalInput> animals;
    @Getter private List<WaterInput> water;
    @Getter private List<AirInput> air;
}


