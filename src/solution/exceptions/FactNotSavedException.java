package exceptions;

public class FactNotSavedException extends RuntimeException {
    private static final String ERROR_FACT_NOT_SAVED =
            "ERROR: Fact not yet saved. Cannot perform action";
    public FactNotSavedException() {
        super(ERROR_FACT_NOT_SAVED);
    }
}
