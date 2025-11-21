package entities.animals.strategies;

import exceptions.AnimalIsStuckException;
import simulation.environmentMap.Cell;
import simulation.environmentMap.EnvironmentMap;

import java.util.Comparator;

public interface IMovementStrategy extends Comparator<Cell> {
    /**
     * Decides which cell on the map an animal will move to.
     * @param map the cell map
     * @param from the current cell
     * @return the next cell
     * @throws AnimalIsStuckException if there are no valid cells to move to
     */
    Cell move(EnvironmentMap map, Cell from) throws AnimalIsStuckException;
}
