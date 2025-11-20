package simulation.events.animal;

import entities.animals.Animal;
import entities.animals.AnimalCellPreference;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.EventPriorities;

public class MoveEvent extends AnimalEvent {


    public MoveEvent(int timestamp, Cell cell) {
        this.timestamp = timestamp;
        this.cell = cell;
        priority = EventPriorities.ANIMAL_MOVE.ordinal();
    }

    @Override
    public void animalAction(final Simulation simulation, final Animal animal) {
        Cell nextCell = simulation.getEnvironmentMap().nextCell(
                cell, new AnimalCellPreference(animal));
        Animal nextCellAnimal = nextCell.getAnimal();
        simulation.addEvent(new MoveEvent(timestamp + 2, nextCell));
        if (nextCellAnimal != null && !animal.isPredator()) {
            return;
        }
        if (nextCellAnimal != null) {
            simulation.addEvent(new FeedEvent(timestamp, nextCell, nextCellAnimal));
        } else {
            simulation.addEvent(new FeedEvent(timestamp, nextCell));
        }
        nextCell.addAnimal(animal);
        cell.removeAnimal();
    }
}
