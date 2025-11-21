package simulation.events.animal;

import entities.animals.Animal;
import exceptions.ExtraFertilizationException;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.EventPriorities;

public final class FeedEvent extends AnimalEvent {
    private static final double  ORGANIC_MATTER_FULL = 0.8;
    private static final double  ORGANIC_MATTER_PART = 0.5;

    private static final int FERTILIZE_DELAY = 1;

    public FeedEvent(final int timestamp, final Cell cell) {
        super(timestamp, cell);
        priority = EventPriorities.ANIMAL_FEED.ordinal();
    }
    private void pushFertilizeEvent(final Simulation simulation,
                                      final double organicMatterAdded) {
        simulation.addEvent(
                new FertilizeEvent(timestamp + FERTILIZE_DELAY, cell, organicMatterAdded)
        );
    }
    @Override
    public void animalAction(final Simulation simulation, final Animal animal) {
        pushFeedEvent(simulation, cell);
        try {
            animal.feed(cell);
            if (cell.getWater() != null && cell.getPlant() == null) {
                /*
                  REF ERROR HERE
                 */
                simulation.addEvent(new FertilizeEvent(timestamp, cell, ORGANIC_MATTER_PART));
            } else {
                pushFertilizeEvent(simulation, ORGANIC_MATTER_PART);
            }
        } catch (ExtraFertilizationException e) {
            pushFertilizeEvent(simulation, ORGANIC_MATTER_FULL);
        } finally {
            cell.update();
        }
    }
}
