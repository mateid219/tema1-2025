package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.CellQualityAgent;
import entities.QualitativeEntity;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for soil entities.
 *
 * <p>This class is designed for extension. Subclasses should ensure they
 * properly handle the entity output building process when overriding methods.
 */
public abstract class Soil extends QualitativeEntity implements CellQualityAgent {
    @Getter @Setter protected double nitrogen;
    @Getter @Setter protected double waterRetention;
    @Getter @Setter protected double soilpH;
    @Getter @Setter protected double organicMatter;

    private static final String OUTPUT_CATEGORY = "soil";

    @Override
    public String getPropertyName() {
        return OUTPUT_CATEGORY;
    }

    public Soil() { }
    public Soil(final SoilInput soilInput) {
        super(soilInput);
        nitrogen = soilInput.getNitrogen();
        waterRetention = soilInput.getWaterRetention();
        soilpH = soilInput.getSoilpH();
        organicMatter = soilInput.getOrganicMatter();
    }

    @Override
    public double cellQualityTerm() {
        return possibilityToGetStuckInSoil();
    }

    /**
     * Subclasses should implement with specific formulas.
     */
    public abstract double possibilityToGetStuckInSoil();

    /**
     * Increases water retention by specified amount
     */
    public final void increaseWaterRetention(final double waterRetentionIncrease) {
        waterRetention += waterRetentionIncrease;
    }
    /**
     * Increases organic matter by specified amount
     */
    public final void fertilize(final double organicMatterAdded) {
        organicMatter += organicMatterAdded;
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
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("nitrogen", nitrogen);
        waterRetention = Math.round(waterRetention * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        objectNode.put("waterRetention", waterRetention);
        objectNode.put("soilpH", soilpH);
        organicMatter = Math.round(organicMatter * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        objectNode.put("organicMatter", organicMatter);
        objectNode.put("soilQuality", calculateFinalScore());
        return objectNode;
    }
}
