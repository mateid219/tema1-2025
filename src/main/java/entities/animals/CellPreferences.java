package entities.Animals;

import lombok.Getter;
import simulation.Cell;

public class CellPreferences implements Comparable<CellPreferences> {
    @Getter
    Cell cell;
    double waterQuality;
    boolean hasPlant;
    boolean hasWater;

    public CellPreferences() {
        cell = null;
        waterQuality = 0.0;
        hasWater = false;
        hasPlant = false;
    }
    public CellPreferences(Cell cell) {
        this();
        this.cell = cell;
        hasWater = cell.getWater() != null && cell.getWater().isScanned();
        if (hasWater) {
            waterQuality = cell.getWater().calculateFinalScore();
        }
        hasPlant = cell.getPlant() != null && cell.getPlant().isScanned();
    }
    public int compareTo(CellPreferences o) {
        if (hasWater && hasPlant && o.hasPlant && o.hasWater) {
            return (int) (waterQuality - o.waterQuality);
        }
        if (hasWater && hasPlant) {
            return 1;
        }
        if (o.hasWater && o.hasPlant) {
            return -1;
        }
        if (hasPlant && o.hasPlant) {
            return 0;
        }
        if (hasPlant) {
            return 1;
        }
        if (o.hasPlant) {
            return -1;
        }
        if (hasWater && o.hasWater) {
            return (int) (waterQuality - o.waterQuality);
        }
        if (hasWater) {
            return 1;
        }
        if (o.hasWater) {
            return -1;
        }
        if (cell == null) {
            return -1;
        }
        if (o.cell == null) {
            return 1;
        }
        return 0;
    }
}
