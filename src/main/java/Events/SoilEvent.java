package Events;

import Simulation.Simulation;
import Simulation.Cell;
import entities.Plants.Plant;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.Queue;

public class SoilEvent extends Event {

    Cell cell;

    private static final String DEAD = "dead";
    private static final double PLANT_GROWTH = 0.2;

    public SoilEvent() { }
    public SoilEvent(int timestamp, Cell cell) {
        super(timestamp);
        this.cell = cell;
        priority = SOIL_EVENT_PRIORITY;
    }
    public void takeEffect(Simulation simulation) {
        Plant plant = cell.getPlant();
        if (plant == null) {
            return;
        }

        plant.grow(PLANT_GROWTH);
        // System.out.println(String.format("Growth at timestamp %d = %f", timestamp, plant.getGrowthLevel()));
        if (DEAD.equals(plant.getMaturity())) {
            cell.setPlant(null);
        } else {
            Queue<Event> eventQueue = simulation.getEventQueue();
            eventQueue.add(new SoilEvent(timestamp + 1, cell));
        }
    }
}
