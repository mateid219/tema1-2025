package entities;

public interface Food extends Scannable {
    /**
     * Classes that implement this should hava a scanTime field with a @Getter.
     * @return the scanTime
     */
    int getScanTime();
}
