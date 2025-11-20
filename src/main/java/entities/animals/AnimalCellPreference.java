package entities.animals;

import simulation.environmentMap.Cell;

import java.util.Comparator;

public record AnimalCellPreference(Animal animal) implements Comparator<Cell> {
    private static final int WATER_SCORE = 100000;
    private static final int PLANT_SCORE = 1000000;
    private static final int WATER_QUALITY_FACTOR = 100;
    private static final int INACCESSIBLE_SCORE = Integer.MIN_VALUE;

    private int calculateCellScore(Cell cell) {
        if (cell == null || !animal.isPredator() && cell.getAnimal() != null) {
            return INACCESSIBLE_SCORE;
        }
        int cellScore = 0;
        if (cell.getWater() != null && cell.getWater().isScanned()) {
            cellScore += WATER_SCORE;
            cellScore += (int) (cell.getWater().calculateFinalScore() * WATER_QUALITY_FACTOR);
        }
        if (cell.getPlant() != null && cell.getPlant().isScanned()) {
            cellScore += PLANT_SCORE;
        }
        return cellScore;
    }

    public int compare(Cell a, Cell b) {
        int aScore = calculateCellScore(a);
        int bScore = calculateCellScore(b);
        return Integer.compare(aScore, bScore);
    }
}