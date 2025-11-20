package simulation.events.animal;

import entities.animals.Animal;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.Event;

public abstract class AnimalEvent extends Event {
     protected Cell cell;




     public AnimalEvent() { }
     public AnimalEvent(final int timestamp, final Cell cell) {
         super(timestamp);
         this.cell = cell;
     }
    protected void pushFertilizeEvent(final Simulation simulation,
                                    final double organicMatterAdded) {
        simulation.addEvent(new FertilizeEvent(timestamp + 1, cell, organicMatterAdded));
    }
    protected void pushFeedEvent(final Simulation simulation, final Cell nextCell) {
        simulation.addEvent(new FeedEvent(timestamp + 1, nextCell));
    }
    @Override
    public final void takeEffect(final Simulation simulation) {
         Animal animal = cell.getAnimal();
         if (animal == null || !animal.isScanned()) {
             return;
         }
         animalAction(simulation, animal);
     }
     public abstract void animalAction(Simulation simulation, Animal animal);

}
