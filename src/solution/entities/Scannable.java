package entities;

import simulation.environmentMap.Cell;
import simulation.terraBot.scanner.ScanParamsVisitor;
import simulation.terraBot.scanner.ScanResult;

public interface Scannable {
    /**
     * Classes that implement this should have a scanned field with a @Getter.
     * @return true if the entity was scanned.
     */
    boolean isScanned();

    /**
     * Classes that implement {@link Scannable} should override this in order to be scanned.
     * @param visitor the scanner
     * @param timestamp the time of the scan
     * @param cell the cell on which the {@link Scannable} is.
     * @return the {@link ScanResult}
     */
    ScanResult accept(ScanParamsVisitor visitor, int timestamp, Cell cell);
}
