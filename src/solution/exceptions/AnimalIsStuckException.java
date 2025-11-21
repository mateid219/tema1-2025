package exceptions;

public class AnimalIsStuckException extends RuntimeException {
    private static final String ANIMAL_IS_STUCK = "Animal can't move.";
    public AnimalIsStuckException() {
        super(ANIMAL_IS_STUCK);
    }
}
