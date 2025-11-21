package simulation.terrabot;

public final class EnergyCosts {
    private EnergyCosts() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    public static final int IMPROVE_ENVIRONMENT = 10;
    public static final int SCAN = 7;
    public static final int LEARN = 2;
}
