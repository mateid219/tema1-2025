package entities.animals.strategies;

import entities.animals.Animal;
import exceptions.ExtraFertilizationException;
import simulation.environmentMap.Cell;

public interface IFeedingStrategy {
    /**
     * Performs the animal feeding.
     * @param cell the cell on which this animal feeds
     * @throws ExtraFertilizationException if the feeding results in more fertilization than normal
     */
    void feed(Animal animal, Cell cell) throws ExtraFertilizationException;
}
