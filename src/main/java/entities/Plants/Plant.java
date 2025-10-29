package entities.Plants;

import entities.Entity;
import fileio.PlantInput;
import fileio.PairInput;

import java.util.ArrayList;
import java.util.List;

public class Plant extends Entity {
    private String state;
    public Plant() { }
    public Plant(final PlantInput plantInput) {
        name = plantInput.getName();
        mass = plantInput.getMass();
        type = plantInput.getType();
        List<PairInput> sectionsList = plantInput.getSections();
        sections = new ArrayList<>(sectionsList);
    }
}
