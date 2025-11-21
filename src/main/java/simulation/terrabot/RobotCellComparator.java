package simulation.terrabot;

import entities.CellQualityAgent;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.air.Air;
import entities.animals.Animal;
import simulation.environmentMap.Cell;

import java.util.Arrays;
import java.util.Comparator;

public final class RobotCellComparator implements Comparator<Cell> {
    public static final RobotCellComparator INSTANCE = new RobotCellComparator();
    private RobotCellComparator() { }
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

    /**
     * Calculates the cell quality.
     * @param cell the cell to be evaluated
     * @return the calculated cell quality
     */
    public int getCellQuality(final Cell cell) {
        return calculateCellQuality(cell);
    }

    @Override
    public int compare(final Cell o1, final Cell o2) {
        int o1Score = calculateCellQuality(o1);
        int o2Score = calculateCellQuality(o2);
        return Integer.compare(o2Score, o1Score);
    }
}
