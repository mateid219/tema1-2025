package fileio;

import java.util.List;

/**
 * Interface for ease of entity placement.
 */
public interface EntityInput {
    /**
     * All entities share this getter for type.
     * @return type
     */
    String getType();
    /**
     * All entities share this getter for name.
     * @return name
     */
    String getName();
    /**
     * All entities share this getter for mass.
     * @return mass
     */
    double getMass();
    /**
     * All entities share this getter for sections.
     * @return sections
     */
    List<PairInput> getSections();
}
