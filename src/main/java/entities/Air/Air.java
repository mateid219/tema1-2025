package entities.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.QualitativeEntity;
import fileio.AirInput;
import fileio.PairInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Air extends QualitativeEntity {
    @Getter @Setter protected double humidity;
    @Getter @Setter protected double temperature;
    @Getter @Setter protected double oxygenLevel;
    @Getter @Setter protected double maxScore;

    public Air() { }
    public Air(final AirInput airInput) {
        name = airInput.getName();
        type = airInput.getType();
        mass = airInput.getMass();
        List<PairInput> sectionsList = airInput.getSections();
        sections = new ArrayList<>(sectionsList);
        humidity = airInput.getHumidity();
        temperature = airInput.getTemperature();
        oxygenLevel = airInput.getOxygenLevel();

    }
    public final double calculateToxicity() {
        double toxicityAQ = MAX_PERCENTAGE * (1.0 - calculateFinalScore() / maxScore);
        return Math.round(toxicityAQ * MAX_PERCENTAGE) / MAX_PERCENTAGE;
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
