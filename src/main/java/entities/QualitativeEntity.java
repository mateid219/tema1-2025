package entities;

public abstract class QualitativeEntity extends Entity {

    public static final double GOOD_THRESHOLD = 70.0;
    public static final double MODERATE_THRESHOLD = 40.0;

    public abstract double calculateScore();
    public double normalize(double score) {
        return Math.max(0, Math.min(MAX_PERCENTAGE, score));
    }
    public double round(double score) {
        return Math.round(score * MAX_PERCENTAGE) / MAX_PERCENTAGE;
    }
    public double finalScore(double score) {
        return round(normalize(score));
    }
    public double calculateFinalScore() {
        double score = calculateScore();
        return finalScore(score);
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
