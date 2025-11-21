package exceptions;

public class ObjectNotFoundException extends RuntimeException {
    private static final String ERROR_NOT_FOUND = "ERROR: Object not found. Cannot perform action";
    public ObjectNotFoundException() {
        super(ERROR_NOT_FOUND);
    }
}
