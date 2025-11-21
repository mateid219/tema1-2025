package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.CellQualityAgent;
import entities.QualitativeEntity;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;

public abstract class Air extends QualitativeEntity implements CellQualityAgent, Weather {
    @Getter @Setter protected double humidity;
    @Getter @Setter protected double temperature;
    @Getter @Setter protected double oxygenLevel;
    @Getter @Setter protected double maxScore;


    private static final String OUTPUT_CATEGORY = "air";
    private static final double MAX_SCORE_COFF = 0.8;

    @Override
    public final String getPropertyName() {
        return OUTPUT_CATEGORY;
    }

    public Air() { }
    public Air(final AirInput airInput) {
        super(airInput);
        humidity = airInput.getHumidity();
        temperature = airInput.getTemperature();
        oxygenLevel = airInput.getOxygenLevel();
    }

    /**
     * Calculates air toxicity using subclass-specific {@link #calculateFinalScore()}
     * @return unrounded, unnormalized toxicity score.
     */
    public final double calculateToxicity() {
        double toxicityAQ = MAX_PERCENTAGE * (1.0 - calculateFinalScore() / maxScore);
        double normalizeScore = Math.max(0, Math.min(MAX_PERCENTAGE, toxicityAQ));
        return Math.round(normalizeScore * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }

    /**
     * Determines toxicity status.
     */
    public final boolean isToxic() {
        double toxicityAQ = MAX_PERCENTAGE * (1.0 - calculateFinalScore() / maxScore);
        return toxicityAQ > (MAX_SCORE_COFF * maxScore);
    }

    @Override
    public final double cellQualityTerm() {
        return calculateToxicity();
    }

    /**
     * Increases oxygen level by specified amount.
     */
    public final void increaseOxygen(final double oxygenLevelIncrease) {
        oxygenLevel += oxygenLevelIncrease;
    }
    /**
     * Increases humidity level by specified amount.
     */
    public final void increaseHumidity(final double humidityIncrease) {
        humidity += humidityIncrease;
    }
    /**
     * Builds the entity output representation.
     *
     * <p>When overriding this method, subclasses should:
     * <ul>
     *   <li>Call super.buildEntityOutput() to include base functionality</li>
     *   <li>Handle any additional properties specific to the subclass</li>
     *   <li>Ensure the output format remains consistent</li>
     * </ul>
     *
     * @return the formatted entity objectNode
     */
    @Override
    public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        humidity = Math.round(humidity * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        objectNode.put("humidity", humidity);
        objectNode.put("temperature", temperature);
        oxygenLevel = Math.round(oxygenLevel * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        objectNode.put("oxygenLevel", oxygenLevel);
        objectNode.put("airQuality", calculateFinalScore());
        return objectNode;
    }

}
