package simulation.terrabot.scanner;

import exceptions.ObjectNotFoundException;
import lombok.Getter;
import simulation.Cell;

public class ScanParams {

    private static final String NONE = "none";

    @Getter private int timestamp;
    @Getter private String color;
    @Getter private String smell;
    @Getter private String sound;
    public ScanParams() { }
    public ScanParams(int timestamp, String color, String smell, String sound) {
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

    public ScanResult accept(ScanParamsVisitor visitor, Cell cell, int timestamp)
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
