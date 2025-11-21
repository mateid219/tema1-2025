package entities.animals.strategies;

import exceptions.AnimalIsStuckException;
import simulation.environmentMap.Cell;
import simulation.environmentMap.EnvironmentMap;

public final class AnimalMovementStrategy extends PredatorMovementStrategy {
    @Override
    protected int calculateCellScore(final Cell cell) {
        if (cell.getAnimal() != null) {
            return INACCESSIBLE_SCORE;
        }
        return super.calculateCellScore(cell);
    }

    @Override
    public Cell move(final EnvironmentMap map, final Cell from) throws AnimalIsStuckException {
        Cell to = map.nextCell(from, this);
        if (to.getAnimal() != null) {
            throw new AnimalIsStuckException();
        }
        return to;
    }
}
