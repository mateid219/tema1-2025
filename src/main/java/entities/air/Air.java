package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.QualitativeEntity;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;

public abstract class Air extends QualitativeEntity {
    @Getter @Setter protected double humidity;
    @Getter @Setter protected double temperature;
    @Getter @Setter protected double oxygenLevel;
    @Getter @Setter protected double maxScore;

    public final static String MOUNTAIN_AIR = "MountainAir";
    public final static String DESERT_AIR = "DesertAir";
    public final static String TEMPERATE_AIR = "TemperateAir";
    public final static String POLAR_AIR = "PolarAir";
    public final static String TROPICAL_AIR = "TropicalAir";

    private static final double MAX_SCORE_COFF = 0.8;

    public static Air createAir(AirInput airInput, int x, int y) {
        return switch (airInput.getType()) {
            case MOUNTAIN_AIR -> new Mountain(airInput, x, y);
            case DESERT_AIR -> new Desert(airInput, x, y);
            case TEMPERATE_AIR -> new Temperate(airInput, x, y);
            case POLAR_AIR -> new Polar(airInput, x, y);
            case TROPICAL_AIR -> new Tropical(airInput, x, y);
            default -> null;
        };
    }
    public Air() { }
    public Air(final AirInput airInput, int x, int y) {
        name = airInput.getName();
        type = airInput.getType();
        mass = airInput.getMass();
        this.x = x;
        this.y = y;
        humidity = airInput.getHumidity();
        temperature = airInput.getTemperature();
        oxygenLevel = airInput.getOxygenLevel();

    }
    public final double calculateToxicity() {
        double toxicityAQ = MAX_PERCENTAGE * (1.0 - calculateFinalScore() / maxScore);
        double normalizeScore = Math.max(0, Math.min(MAX_PERCENTAGE, toxicityAQ));
        return Math.round(normalizeScore * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }
    public final boolean isToxic() {
        double toxicityAQ = MAX_PERCENTAGE * (1.0 - calculateFinalScore() / maxScore);
        return toxicityAQ > (MAX_SCORE_COFF * maxScore);
    }
    public void addOxygen(double oxygenLevel) {
        this.oxygenLevel += oxygenLevel;
        this.oxygenLevel = Math.round(this.oxygenLevel * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }
    public void increaseHumidity(double humidity) {
        this.humidity += humidity;
        this.humidity = Math.round(this.humidity * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("humidity", humidity);
        objectNode.put("temperature", temperature);
        objectNode.put("oxygenLevel", oxygenLevel);
        objectNode.put("airQuality", calculateFinalScore());
        return objectNode;
    }

}
