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

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
@NoArgsConstructor
public final class Simulation {

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
        terraBot = new TerraBot(energyPoints, environmentMap.getMap()[0][0]);
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

    /**
     * Adds a new event to the {@link #eventQueue}.
     * @param event the new event
     */
    public void addEvent(final Event event) {
        eventQueue.add(event);
    }
    /**
     * Similar to {@link #addEvent(Event)}.
     * Adds a {@link List} of new events to the {@link #eventQueue}.
     * @param events the list of new events
     */
    public void addEvents(final List<Event> events) {
        eventQueue.addAll(events);
    }
    /**
     * Delegates execution of command to {@link #terraBot}
     */
    public ObjectNode printEnvConditions() {
        return terraBot.printEnvConditions();
    }
    /**
     * Delegates execution of command to {@link #environmentMap}
     */
    public ArrayNode printMap() {
        return environmentMap.buildMapOutput();
    }

}
