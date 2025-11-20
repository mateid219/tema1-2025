package simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SimulationInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import simulation.environmentMap.EnvironmentMap;
import simulation.events.Event;
import simulation.terrabot.TerraBot;
import simulation.terrabot.scanner.EventScheduler;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
@NoArgsConstructor
public final class Simulation {

    @Getter private EventScheduler eventScheduler;
    @Getter private EnvironmentMap environmentMap;
    @Getter @Setter private boolean started = false;
    @Getter @Setter private boolean ended = false;
    private final Queue<Event> eventQueue = new PriorityQueue<Event>();
    @Getter private TerraBot terraBot;

    public Simulation(final SimulationInput simulationInput) {
        var territorySectionParamsInput = simulationInput.getTerritorySectionParams();
        environmentMap = new EnvironmentMap(simulationInput.getTerritoryDim());
        environmentMap.placeEntities(territorySectionParamsInput);
        int energyPoints = simulationInput.getEnergyPoints();
        terraBot = new TerraBot(energyPoints, environmentMap.cellAt(0, 0));
        eventScheduler = new EventScheduler(this);
    }

    /**
     * Executes all events which are past(or at) their timestamp.
     * @param timestamp the timestamp of the command that called this
     */
    public void update(final int timestamp) {
        while (!eventQueue.isEmpty() && eventQueue.peek().getTimestamp() <= timestamp) {
            Event event = eventQueue.poll();
            event.takeEffect(this);
        }
    }
    public void addEvent(Event event) {
        eventQueue.add(event);
    }
    public void addEvents(List<Event> events) {
        eventQueue.addAll(events);
    }
    /**
     * Passes execution of command to {@link #terraBot}
     */
    public ObjectNode printEnvConditions() {
        return terraBot.printEnvConditions();
    }
    /**
     * Passes execution of command to {@link #environmentMap}
     */
    public ArrayNode printMap() {
        return environmentMap.buildMapOutput();
    }

}
