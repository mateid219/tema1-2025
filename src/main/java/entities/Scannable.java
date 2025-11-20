package entities;

import simulation.environmentMap.Cell;
import simulation.terrabot.scanner.ScanParamsVisitor;
import simulation.terrabot.scanner.ScanResult;

public interface Scannable {
    ScanResult accept(ScanParamsVisitor visitor, int timestamp, Cell cell);
}
