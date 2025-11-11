package Events;

import Simulation.Cell;
import Simulation.Simulation;
import Simulation.Map;
import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;

import java.util.Queue;

public class AnimalEvent extends Event {
     Cell cell;
     Map map;
     int type;

     private static final String PARASITE = "Parasites";
     private static final String CARNIVORE = "Carnivores";

     private static final String WELL_FED = "well-fed";
     private static final String HUNGRY = "well-fed";

     public static final int MOVE = 1;
     public static final int FEED = 2;

     private static final double  ORGANIC_MATTER_FULL = 0.8;
     private static final double  ORGANIC_MATTER_PART = 0.5;

     public AnimalEvent() { }
     public AnimalEvent(int timestamp, Cell cell, int type) {
         super(timestamp);
         this.cell = cell;
         this.type = type;
         priority = (type == MOVE ? ANIMAL_MOVE_PRIORITY : ANIMAL_FEED_PRIORITY);
     }
     public void takeEffect(Simulation simulation) {
         Animal animal = cell.getAnimal();
         if (animal == null || !animal.isScanned()) {
             return;
         }
         Queue<Event> eventQueue = simulation.getEventQueue();
         if (type == MOVE) {
            Cell nextCell = simulation.animalNextCell(cell);
            Animal nextCellAnimal = nextCell.getAnimal();
            nextCell.setAnimal(animal);
            cell.setAnimal(null);
            if (nextCellAnimal != null) {
                if (! CARNIVORE.equals(animal.getType()) && ! PARASITE.equals(animal.getType())) {
                    return;
                }
                animal.eat(nextCellAnimal);
                animal.setState(WELL_FED);
            } else {
                eventQueue.add(new AnimalEvent(timestamp, nextCell, FEED));
                animal.setState(HUNGRY);
            }
            System.out.print("{\n" + animal.getName() + " moved to cell");
            System.out.printf("(%d,%d) at timestamp %d\n}\n",nextCell.getX(),nextCell.getY(),timestamp);
            eventQueue.add(new AnimalEvent(timestamp + 2, nextCell, MOVE));
            return;
         }
         eventQueue.add(new AnimalEvent(timestamp + 1, cell, FEED));
         Water water = cell.getWater();
         Plant plant = cell.getPlant();
         Soil soil = cell.getSoil();
         if ((water == null || !water.isScanned()) &&
                 (plant == null || !plant.isScanned() )){
             animal.setState(HUNGRY);
             return;
         }
         if (water == null || !water.isScanned()) {
             animal.eat(plant);
             cell.setPlant(null);
             animal.setState(WELL_FED);
             soil.fertilize(ORGANIC_MATTER_PART);
             return;
         }
         if (plant == null || !plant.isScanned() ) {
             animal.drink(water);
             if (water.getMass() == 0.0) {
                 cell.setWater(null);
             }
             animal.setState(WELL_FED);
             soil.fertilize(ORGANIC_MATTER_PART);
             return;
         }
         if (water.getScanTime() == plant.getScanTime()) {
             animal.eat(plant);
             cell.setPlant(null);
             animal.drink(water);
             if (water.getMass() == 0.0) {
                 cell.setWater(null);
             }
             animal.setState(WELL_FED);
             soil.fertilize(ORGANIC_MATTER_FULL);
             return;
         }
         if (water.getScanTime() < plant.getScanTime()) {
             animal.drink(water);
             if (water.getMass() == 0.0) {
                 cell.setWater(null);
             }
         } else {
             animal.eat(plant);
             cell.setPlant(null);
         }
         animal.setState(WELL_FED);
         soil.fertilize(ORGANIC_MATTER_PART);
    }
}
