package fileio;

import java.util.List;

/**
 * Interface for ease of entity placement.
 */
public interface EntityInput {
    /**
     * Common getter for sections.
     * @return sections
     */
    List<PairInput> getSections();
}
