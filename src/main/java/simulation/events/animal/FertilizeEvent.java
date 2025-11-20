package simulation.events.animal;

import entities.Soil.Soil;
import entities.animals.Animal;
import simulation.Simulation;
import simulation.environmentMap.Cell;
import simulation.events.EventPriorities;

public class FertilizeEvent extends AnimalEvent {
    private final double organicMatterAdded;

    public FertilizeEvent(int timestamp, Cell cell, double organicMatterAdded) {
        this.timestamp = timestamp;
        this.cell = cell;
        this.organicMatterAdded = organicMatterAdded;
        priority = EventPriorities.ANIMAL_FERTILIZE.ordinal();
    }

    @Override
    public void animalAction(final Simulation simulation, final Animal animal) {
        if (!animal.ate()) {
            return;
        }
        Soil soil = cell.getSoil();
        soil.fertilize(organicMatterAdded);
    }
}
