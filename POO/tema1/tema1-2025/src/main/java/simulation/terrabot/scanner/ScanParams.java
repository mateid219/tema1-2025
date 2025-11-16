package simulation.terrabot.scanner;

import exceptions.ObjectNotFoundException;
import lombok.Getter;
import simulation.Cell;

public final class ScanParams {

    private static final String NONE = "none";

    @Getter private int timestamp;
    @Getter private String color;
    @Getter private String smell;
    @Getter private String sound;
    public ScanParams() { }
    public ScanParams(final int timestamp, final String color,
                      final String smell, final String sound) {
        this.timestamp = timestamp;
        this.color = color;
        this.smell = smell;
        this.sound = sound;
    }

    private boolean scannedIsWater() {
        return NONE.equals(color) && NONE.equals(smell) && NONE.equals(sound);
    }
    private boolean scannedIsPlant() {
        return !NONE.equals(color) && !NONE.equals(smell) && NONE.equals(sound);
    }
    private boolean scannedIsAnimal() {
        return !NONE.equals(color) && !NONE.equals(smell) && !NONE.equals(sound);
    }

    /**
     * Redirects the scan operation to the corresponding entity.
     * @param visitor the scanner
     * @param cell the cell on which the scan is performed
     * @return the scan results
     * @throws ObjectNotFoundException if no scannable object was found on the cell
     */
    public ScanResult accept(final ScanParamsVisitor visitor, final Cell cell)
            throws ObjectNotFoundException {
        if (scannedIsAnimal() && cell.getAnimal() != null) {
            return cell.getAnimal().accept(visitor, timestamp, cell);
        }
        if (scannedIsWater() && cell.getWater() != null) {
            return cell.getWater().accept(visitor, timestamp, cell);
        }
        if (scannedIsPlant() && cell.getPlant() != null) {
            return cell.getPlant().accept(visitor, timestamp, cell);
        }
        throw new ObjectNotFoundException();
    }

}
