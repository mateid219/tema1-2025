package my;

import entities.Soil.SoilArrayList;
import fileio.SoilInput;
import fileio.TerritorySectionParamsInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TerritorySectionParams {
    @Getter @Setter private SoilArrayList soil;

    public TerritorySectionParams() { }
    public TerritorySectionParams(final TerritorySectionParamsInput territorySectionParamsInput) {
        List<SoilInput> soilInputList = territorySectionParamsInput.getSoil();
        ArrayList<SoilInput> soilInputArrayList = new ArrayList<>(soilInputList);
        soil = new SoilArrayList(soilInputArrayList);
    }
}
