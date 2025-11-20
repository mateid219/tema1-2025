package simulation.events;

import entities.air.Air;
import entities.animals.Animal;
import lombok.NoArgsConstructor;
import simulation.Simulation;
import simulation.environmentMap.Cell;
@NoArgsConstructor
public final class AirEvent extends Event {
    private Cell cell;

    public AirEvent(final int timestamp, final Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = EventPriorities.AIR_EVENT.ordinal();
    }
    @Override
    public void takeEffect(final Simulation simulation) {
        Air air = cell.getAir();
        Animal animal = cell.getAnimal();
        if (animal == null || !animal.isScanned()) {
            return;
        }
        if (air.isToxic()) {
            animal.sicken();
        }
        simulation.addEvent(new AirEvent(timestamp + 1, cell));
    }
}
