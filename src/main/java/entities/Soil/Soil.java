package entities.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.QualitativeEntity;
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
public abstract class Soil extends QualitativeEntity {
    @Getter @Setter protected double nitrogen;
    @Getter @Setter protected double waterRetention;
    @Getter @Setter protected double soilpH;
    @Getter @Setter protected double organicMatter;


    public Soil() { }
    public Soil(final SoilInput soilInput) {
        name = soilInput.getName();
        type = soilInput.getType();
        mass = soilInput.getMass();
        List<PairInput> sectionsList = soilInput.getSections();
        sections = new ArrayList<>(sectionsList);

        nitrogen = soilInput.getNitrogen();
        waterRetention = soilInput.getWaterRetention();
        soilpH = soilInput.getSoilpH();
        organicMatter = soilInput.getOrganicMatter();
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
