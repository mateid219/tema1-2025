package exceptions;

public class ExtraFertilizationException extends RuntimeException {
    private static final String EXTRA_FERTILIZATION =
            "The soil was fertilized with extra organicMatter.";
    public ExtraFertilizationException() {
        super(EXTRA_FERTILIZATION);
    }
}
