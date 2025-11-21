package exceptions;

public class UnknownImprovementException extends RuntimeException {
    private static final String UNKNOWN_IMPROVEMENT = "Unknown improvement: ";
    public UnknownImprovementException(final String type) {
        super(UNKNOWN_IMPROVEMENT + type);
    }
}
