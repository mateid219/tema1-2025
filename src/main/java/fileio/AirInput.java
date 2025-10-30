package fileio;

import lombok.Getter;

import java.util.List;

public class AirInput implements InputEntity {
    @Getter private String type;
    @Getter private String name;
    @Getter private double mass;
    @Getter private double humidity;
    @Getter private double temperature;
    @Getter private double oxygenLevel;
    @Getter private double altitude;
    @Getter private double pollenLevel;
    @Getter private double co2Level;
    @Getter private double iceCrystalConcentration;
    @Getter private double dustParticles;
    @Getter private List<PairInput> sections;
}

