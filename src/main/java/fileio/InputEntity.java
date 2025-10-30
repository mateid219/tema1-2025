package fileio;

import java.util.List;

public interface InputEntity {
    /**
     *
     * @return
     */
    String getName();
    /**
     *
     * @return
     */
    String getType();
    /**
     *
     * @return
     */
    double getMass();
    /**
     *
     * @return
     */
    List<PairInput> getSections();
}
