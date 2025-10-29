package entities.Plants;

import fileio.PlantInput;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class PlantArrayList {
    @Getter @Setter private ArrayList<Plant> plantArrayList;
    public PlantArrayList() { }
    public PlantArrayList(final ArrayList<PlantInput> plantInputArrayList) {
        plantArrayList = new ArrayList<>();
        for (PlantInput plantInput : plantInputArrayList) {
            Plant plant = new Plant(plantInput);
            plantArrayList.add(plant);
        }
    }
}
