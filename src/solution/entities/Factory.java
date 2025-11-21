package entities;

import fileio.EntityInput;

public abstract class Factory {
    private Factory() {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    abstract Entity create(EntityInput entityInput);
}
