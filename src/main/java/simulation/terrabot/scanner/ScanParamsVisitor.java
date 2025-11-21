package simulation.terrabot.scanner;

import entities.animals.Animal;
import entities.Plants.Plant;
import entities.Water.Water;
import simulation.environmentMap.Cell;

public interface ScanParamsVisitor {
    /**
     * Classes that implement animal scanning logic should override this.
     * @param animal the animal to be scanned
     * @param timestamp the time of the scan
     * @param cell the cell on which the animal resides
     * @return the details of the scan operation
     */
    ScanResult visitAnimal(Animal animal, int timestamp, Cell cell);
    /**
     * Classes that implement water scanning logic should override this.
     * @param water the water to be scanned
     * @param timestamp the time of the scan
     * @param cell the cell on which the water is on
     * @return the details of the scan operation
     */
    ScanResult visitWater(Water water, int timestamp, Cell cell);
    /**
     * Classes that implement plant scanning logic should override this.
     * @param plant the plant to be scanned
     * @param timestamp the time of the scan
     * @param cell the cell on which the plant is on
     * @return the details of the scan operation
     */
    ScanResult visitPlant(Plant plant, int timestamp, Cell cell);
}
