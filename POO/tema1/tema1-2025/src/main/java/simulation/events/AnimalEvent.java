package simulation.events;

import simulation.Cell;
import simulation.Simulation;
import entities.animals.Animal;
import entities.Plants.Plant;
import entities.Soil.Soil;
import entities.Water.Water;

import java.util.Queue;

public final class AnimalEvent extends Event {
     private Cell cell;
     private Animal prey;
     private int type;


     private static final String PARASITE = "Parasites";
     private static final String CARNIVORE = "Carnivores";

     private static final String WELL_FED = "well-fed";
     private static final String HUNGRY = "hungry";

     public static final int MOVE = 1;
     public static final int FEED = 2;
     public static final int FERTILIZE = 3;
     public static final int FERTILIZE_FULL = 4;

     private static final double  ORGANIC_MATTER_FULL = 0.8;
     private static final double  ORGANIC_MATTER_PART = 0.5;

     public AnimalEvent() { }
     public AnimalEvent(final int timestamp, final Cell cell, final int type) {
         super(timestamp);
         this.cell = cell;
         this.type = type;
         this.prey = null;
         priority = switch (type) {
             case MOVE -> EventPriorities.ANIMAL_MOVE.ordinal();
             case FEED -> EventPriorities.ANIMAL_FEED.ordinal();
             default -> EventPriorities.ANIMAL_FERTILIZE.ordinal();
         };
     }
     public AnimalEvent(final int timestamp, final Cell cell, final int type, final Animal prey) {
        this(timestamp, cell, type);
        this.prey = prey;
    }
    @Override
     public void takeEffect(final Simulation simulation) {
         Queue<Event> eventQueue = simulation.getEventQueue();
         Soil soil = cell.getSoil();
         Animal animal = cell.getAnimal();
         switch (type) {
             case FERTILIZE:
                 soil.fertilize(ORGANIC_MATTER_PART);
                 System.out.println(" at timestamp " + timestamp);
                 return;
             case FERTILIZE_FULL:
                 soil.fertilize(ORGANIC_MATTER_FULL);
                 return;
             case MOVE:
                 if (animal == null || !animal.isScanned()) {
                     return;
                 }
                 Cell nextCell = simulation.getEnvironmentMap().animalNextCell(
                         cell, animal.getType());
                 Animal nextCellAnimal = nextCell.getAnimal();
                 if (nextCellAnimal != null) {
                     if (!CARNIVORE.equals(animal.getType())
                             && !PARASITE.equals(animal.getType())) {
                         return;
                     }
                     eventQueue.add(new AnimalEvent(timestamp, nextCell, FEED, nextCellAnimal));
                     animal.setState(WELL_FED);
                 } else {
                     eventQueue.add(new AnimalEvent(timestamp, nextCell, FEED));
                     animal.setState(HUNGRY);
                 }
                 nextCell.setAnimal(animal);
                 cell.setAnimal(null);
                 eventQueue.add(new AnimalEvent(timestamp + 2, nextCell, MOVE));
                 return;
             case FEED:
                 if (animal == null || !animal.isScanned()) {
                     return;
                 }
                 eventQueue.add(new AnimalEvent(timestamp + 1, cell, FEED));
                 if (prey != null) {
                     animal.eat(prey);
                     System.out.println(" at timestamp " + timestamp);
                     animal.setState(WELL_FED);
                     eventQueue.add(new AnimalEvent(timestamp + 1, cell, FERTILIZE));
                     return;
                 }
                 Water water = cell.getWater();
                 Plant plant = cell.getPlant();
                 if ((water == null || !water.isScanned())
                         && (plant == null || !plant.isScanned())) {
                     animal.setState(HUNGRY);
                     return;
                 }
                 if (water == null || !water.isScanned()) {
                     animal.eat(plant);
                     System.out.println(" at timestamp " + timestamp);
                     cell.setPlant(null);
                     animal.setState(WELL_FED);
                     animal.setFull(false);
                     eventQueue.add(new AnimalEvent(timestamp + 1, cell, FERTILIZE));
                     return;
                 }
                 if (plant == null || !plant.isScanned()) {
                     animal.drink(water);
                     System.out.println(" at timestamp " + timestamp);
                     if (water.getMass() == 0.0) {
                         cell.setWater(null);
                     }
                     animal.setState(WELL_FED);
                     animal.setFull(false);
                     eventQueue.add(new AnimalEvent(timestamp, cell, FERTILIZE));
                     return;
                 }
                 if (water.getScanTime() == plant.getScanTime()) {
                     animal.eat(plant);
                     System.out.println(" at timestamp " + timestamp);
                     cell.setPlant(null);
                     animal.drink(water);
                     System.out.println(" at timestamp " + timestamp);
                     if (water.getMass() == 0.0) {
                         cell.setWater(null);
                     }
                     animal.setState(WELL_FED);
                     animal.setFull(true);
                     eventQueue.add(new AnimalEvent(timestamp + 1, cell, FERTILIZE_FULL));
                     return;
                 }
                 if (water.getScanTime() < plant.getScanTime()) {
                     animal.drink(water);
                     System.out.println(" at timestamp " + timestamp);
                     if (water.getMass() == 0.0) {
                         cell.setWater(null);
                     }
                 } else {
                     animal.eat(plant);
                     cell.setPlant(null);
                 }
                 animal.setState(WELL_FED);
                 animal.setFull(false);
                 eventQueue.add(new AnimalEvent(timestamp + 1, cell, FERTILIZE));
             default:
         }
     }
}
