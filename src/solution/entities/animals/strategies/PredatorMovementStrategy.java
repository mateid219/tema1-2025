package entities.animals.strategies;

import entities.plants.Plant;
import entities.water.Water;
import entities.animals.Animal;
import entities.animals.AnimalStates;
import simulation.environmentMap.Cell;
import simulation.environmentMap.EnvironmentMap;

public class PredatorMovementStrategy implements IMovementStrategy {
    private static final int PLANT_SCORE = 100000;
    private static final int WATER_QUALITY_FACTOR = 100;
    protected static final int INACCESSIBLE_SCORE = Integer.MIN_VALUE;

    private void eat(final Animal predator, final Animal prey) {
        predator.setMass(predator.getMass() + prey.getMass());
        predator.setState(AnimalStates.WELL_FED);
    }

    /**
     * Subclasses that overwrite this should return an {@link Integer} score.
     * Calculates the score of the cell:
     * <ul>
     *     <li><code>+ 100000</code> if the cell has a plant </li>
     *     <li><code>+ 100 * {@link Water#calculateFinalScore()}</code> if the cell has water</li>
     * </ul>
     * @param cell the evaluated cell
     * @return the cell score
     */
    protected int calculateCellScore(final Cell cell) {
        int cellScore = 0;
        Water water = cell.getWater();
        if (water != null && water.isScanned()) {
            cellScore += (int) (cell.getWater().calculateFinalScore() * WATER_QUALITY_FACTOR);
        }
        Plant plant = cell.getPlant();
        if (plant != null && plant.isScanned()) {
            cellScore += PLANT_SCORE;
        }
        return cellScore;
    }
    @Override
    public final int compare(final Cell a, final Cell b) {
        int aScore = calculateCellScore(a);
        int bScore = calculateCellScore(b);
        return Integer.compare(aScore, bScore);
    }

    /**
     * Subclasses that overwrite this should use {@link EnvironmentMap#nextCell}
     * and pass themselves as second argument.
     */
    @Override
    public Cell move(final EnvironmentMap map, final Cell from)  {
        Cell to = map.nextCell(from, this);
        if (to.getAnimal() != null) {
            eat(from.getAnimal(), to.getAnimal());
        }
        return to;
    }
}
