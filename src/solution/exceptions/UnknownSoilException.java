package exceptions;

public class UnknownSoilException extends RuntimeException {
    public UnknownSoilException(final String message) {
        super(message);
    }
}
