package entities;

public interface CellQualityAgent {
    /**
     * Classes that implement this should provide the contribution
     * to cell quality.
     * @return the contribution to cell quality
     */
    double cellQualityTerm();
}
