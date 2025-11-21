package exceptions;

public class DoesNotAffectException extends RuntimeException {
    public static final String ERROR_DOES_NOT_AFFECT = "ERROR: The weather change does not affect"
            + " the environment. Cannot perform action";
    public DoesNotAffectException() {
        super(ERROR_DOES_NOT_AFFECT);
    }
}
