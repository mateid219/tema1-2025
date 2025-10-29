package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import fileio.PairInput;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for soil entities.
 *
 * <p>This class is designed for extension. Subclasses should ensure they
 * properly handle the entity output building process when overriding methods.
 */
public abstract class Soil extends Entity {
    @Getter @Setter private String type;
    @Getter @Setter private double nitrogen;
    @Getter @Setter private double waterRetention;
    @Getter @Setter private double soilpH;
    @Getter @Setter private double organicMatter;
    @Getter @Setter private ArrayList<PairInput> sections;


    private static final double MAX_PERCENTAGE = 100.0;
    private static final double GOOD_THRESHOLD = 70.0;
    private static final double MODERATE_THRESHOLD = 40.0;

    public Soil() { }
    public Soil(final SoilInput soilInput) {
        super.setName(soilInput.getName());
        super.setMass(soilInput.getMass());
        type = soilInput.getType();
        nitrogen = soilInput.getNitrogen();
        waterRetention = soilInput.getWaterRetention();
        soilpH = soilInput.getSoilpH();
        organicMatter = soilInput.getOrganicMatter();
        List<PairInput> sectionsList = soilInput.getSections();
        sections = new ArrayList<>(sectionsList);
    }
    abstract double calculateScore();

    public final double calculateFinalScore() {
        double score = calculateScore();
        double normalizeScore = Math.max(0, Math.min(MAX_PERCENTAGE, score));
        return Math.round(normalizeScore * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }
    public final String interpretSoilQuality() {
        double finalScore = calculateFinalScore();
        if (finalScore >= GOOD_THRESHOLD) {
            return "good";
        }
        if (finalScore >= MODERATE_THRESHOLD) {
            return "moderate";
        }
        return "poor";
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
     * @return the formatted entity output string
     */
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("nitrogen", nitrogen);
        objectNode.put("waterRetention", waterRetention);
        objectNode.put("soilpH", soilpH);
        objectNode.put("organicMatter", organicMatter);
        objectNode.put("soilQuality", calculateFinalScore());
        return objectNode;
    }
}
