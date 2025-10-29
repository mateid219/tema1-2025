package entities;

public abstract class QualitativeEntity extends Entity {
    public static final double MAX_PERCENTAGE = 100.0;
    public static final double GOOD_THRESHOLD = 70.0;
    public static final double MODERATE_THRESHOLD = 40.0;

    public abstract double calculateScore();
    public final double calculateFinalScore() {
        double score = calculateScore();
        double normalizeScore = Math.max(0, Math.min(MAX_PERCENTAGE, score));
        return Math.round(normalizeScore * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }
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
