package exceptions;

public class UnknownCommandException extends RuntimeException {
    private static final String UNKNOWN_COMMAND = "Unknown command.";
    public UnknownCommandException() {
        super(UNKNOWN_COMMAND);
    }
}
