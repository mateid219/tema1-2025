package exceptions;

public class BatteryException extends RuntimeException {
    private static final String ERROR_BATTERY =
            "ERROR: Not enough battery left. Cannot perform action";
    public BatteryException() {
        super(ERROR_BATTERY);
    }
}
