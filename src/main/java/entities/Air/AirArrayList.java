package entities.Air;

import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class AirArrayList {
    @Getter @Setter private ArrayList<Air> airArrayList;
    public AirArrayList() { }
    public AirArrayList(final ArrayList<AirInput> airInputArrayList) {
        airArrayList = new ArrayList<Air>();
        for (AirInput airInput : airInputArrayList) {
            Air air = null;
            switch (airInput.getType()) {
                case "MountainAir":
                    air = new Mountain(airInput);
                    break;
                case "DesertAir":
                    air = new Desert(airInput);
                    break;
                case "TemperateAir":
                    air = new Temperate(airInput);
                    break;
                case "PolarAir":
                    air = new Polar(airInput);
                    break;
                case "TropicalAir":
                    air = new Tropical(airInput);
                    break;
            }
            airArrayList.add(air);
        }
    }
}
