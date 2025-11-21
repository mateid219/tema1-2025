package exceptions;

public class SubjectNotSavedException extends RuntimeException {
    private static final String ERROR_SUBJECT_NOT_SAVED =
            "ERROR: Subject not yet saved. Cannot perform action";
    public SubjectNotSavedException() {
        super(ERROR_SUBJECT_NOT_SAVED);
    }
}
