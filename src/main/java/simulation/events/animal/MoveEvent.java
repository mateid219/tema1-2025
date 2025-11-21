package simulation.events.animal;

import entities.animals.Animal;
import exceptions.AnimalIsStuckException;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.EventPriorities;

public final class MoveEvent extends AnimalEvent {
    private static final int MOVE_DELAY = 2;
    private static final int FEED_DELAY = 0;

    public MoveEvent(final int timestamp, final Cell cell) {
        this.timestamp = timestamp;
        this.cell = cell;
        priority = EventPriorities.ANIMAL_MOVE.ordinal();
    }
    private void pushMoveEvent(final Simulation simulation, final Cell cell) {
        simulation.addEvent(new MoveEvent(timestamp + MOVE_DELAY, cell));
    }
    @Override
    public void animalAction(final Simulation simulation, final Animal animal) {
        try {
            Cell nextCell = animal.move(simulation.getEnvironmentMap(), cell);
            nextCell.addAnimal(animal);
            cell.removeAnimal();
            pushMoveEvent(simulation, nextCell);
            /*
                ERROR REF
             */
            simulation.addEvent(new FeedEvent(timestamp + FEED_DELAY, nextCell));
        } catch (AnimalIsStuckException e) {
            pushMoveEvent(simulation, cell);
        }
    }
}
