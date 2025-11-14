package exceptions;

public class AlreadyStartedException extends RuntimeException {
    public AlreadyStartedException(String message) {
        super(message);
    }
}
