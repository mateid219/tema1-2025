package entities.Water;

import fileio.WaterInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class WaterArrayList {
    @Getter @Setter private ArrayList<Water> waterArrayList;
    public WaterArrayList() { }
    public WaterArrayList(final ArrayList<WaterInput> waterInputArrayList) {
        waterArrayList = new ArrayList<>();
        for (WaterInput waterInput : waterInputArrayList) {
            Water water = new Water(waterInput);
            waterArrayList.add(water);
        }
    }
}
