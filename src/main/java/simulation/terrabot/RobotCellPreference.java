package simulation.terrabot;

import entities.CellQualityAgent;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.air.Air;
import entities.animals.Animal;
import simulation.environmentMap.Cell;

import java.util.Arrays;
import java.util.Comparator;

public final class RobotCellPreference implements Comparator<Cell> {
    private int calculateCellQuality(final Cell cell) {
        Soil soil = cell.getSoil();
        Air air = cell.getAir();
        Animal animal = cell.getAnimal();
        Plant plant = cell.getPlant();
        double score = 0.0;
        int count = 0;
        for (CellQualityAgent qualityAgent : Arrays.asList(soil, air, animal, plant)) {
            if (qualityAgent != null) {
                score += qualityAgent.cellQualityTerm();
                count++;
            }
        }
        double mean = Math.abs(score / count);
        return (int) Math.round(mean);
    }
    public int getCellQuality(final Cell cell) {
        return calculateCellQuality(cell);
    }
    public int compare(Cell a, Cell b) {
        int aScore = calculateCellQuality(a);
        int bScore = calculateCellQuality(b);
        return Integer.compare(bScore, aScore);
    }
}
