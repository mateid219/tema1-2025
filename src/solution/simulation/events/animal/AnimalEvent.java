package simulation.events.animal;

import entities.animals.Animal;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.Event;

public abstract class AnimalEvent extends Event {
    private static final int FEED_DELAY = 1;

    protected Cell cell;
    public AnimalEvent() { }
    public AnimalEvent(final int timestamp, final Cell cell) {
        super(timestamp);
        this.cell = cell;
    }
    protected final void pushFeedEvent(final Simulation simulation, final Cell nextCell) {
        simulation.addEvent(new FeedEvent(timestamp + FEED_DELAY, nextCell));
    }
    @Override
    public final void takeEffect(final Simulation simulation) {
         Animal animal = cell.getAnimal();
         if (animal == null || !animal.isScanned()) {
             return;
         }
         animalAction(simulation, animal);
     }
     abstract void animalAction(Simulation simulation, Animal animal);
}
