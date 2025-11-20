package exceptions;

public class AlreadyStartedException extends RuntimeException {
    public AlreadyStartedException(final String message) {
        super(message);
    }
}
