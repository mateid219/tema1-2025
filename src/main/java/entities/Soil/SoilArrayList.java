package entities.Soil;

import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class SoilArrayList {
    @Getter @Setter ArrayList<Soil> soilArrayList;

    public SoilArrayList() {
        soilArrayList = new ArrayList<Soil>();
    }
    public SoilArrayList(ArrayList<SoilInput> soilInputArrayList) {
        this();
        for (SoilInput soilInput : soilInputArrayList) {
            Soil soil = null;
            switch (soilInput.getType()) {
                case "ForestSoil":
                    soil = new ForestSoil(soilInput);
                    break;
                case "DesertSoil":
                    soil = new DesertSoil(soilInput);
                    break;
                case "SwampSoil":
                    soil = new SwampSoil(soilInput);
                    break;
                case "TundraSoil":
                    soil = new TundraSoil(soilInput);
                    break;
                case "GrasslandSoil":
                    soil = new GrasslandSoil(soilInput);
                    break;
            }
            soilArrayList.add(soil);
        }
    }
}
