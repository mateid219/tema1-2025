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
        super(plantInput);
    }
}
