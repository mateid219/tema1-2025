package entities;

import fileio.EntityInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class QualitativeEntity extends Entity {

    public static final double GOOD_THRESHOLD = 70.0;
    public static final double MODERATE_THRESHOLD = 40.0;
    public QualitativeEntity(final EntityInput entityInput) {
        super(entityInput);
    }
    /**
     * Subclasses must implement this to calculate entity-specific score.
     * @return the unrounded, unnormalized score.
     */
    public abstract double calculateScore();

    /**
     * Normalizes a score.
     * @param score the unrounded, unnormalized entity score
     * @return the normalized score
     */
    public final double normalize(final double score) {
        return Math.max(0, Math.min(MAX_PERCENTAGE, score));
    }
    /**
     * Rounds a score.
     * @param score the unrounded, normalized entity score
     * @return the rounded score
     */
    public final double round(final double score) {
        return Math.round(score * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }

    /**
     * Applies round and normalize operations.
     */
    public final double finalScore(final double score) {
        return round(normalize(score));
    }

    /**
     * Calls the subclass-specific {@link #calculateScore()} method.
     * Normalizes and rounds the score.
     * @return the final entity score
     */
    public double calculateFinalScore() {
        double score = calculateScore();
        return finalScore(score);
    }

    /**
     * Interprets the quality of the score.
     */
    public final String interpretQuality() {
        double finalScore = calculateFinalScore();
        if (finalScore >= GOOD_THRESHOLD) {
            return "good";
        }
        if (finalScore >= MODERATE_THRESHOLD) {
            return "moderate";
        }
        return "poor";
    }
}
