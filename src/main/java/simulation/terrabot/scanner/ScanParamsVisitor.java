package simulation.terrabot.scanner;

import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Water.Water;
import simulation.Cell;

public interface ScanParamsVisitor {
    ScanResult visitAnimal(Animal animal, int timestamp, Cell cell);
    ScanResult visitWater(Water water, int timestamp, Cell cell);
    ScanResult visitPlant(Plant plant, int timestamp, Cell cell);
}
