package simulation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SimulationInput;
import lombok.Getter;
import lombok.Setter;
import simulation.events.Event;
import simulation.terrabot.TerraBot;

import java.util.PriorityQueue;
import java.util.Queue;

public final class Simulation {

    @Getter private TerritorySectionParams territorySectionParams;
    @Getter private EnvironmentMap environmentMap;
    @Getter @Setter private boolean started;
    @Getter @Setter private boolean ended;
    @Getter private final Queue<Event> eventQueue;
    @Getter private TerraBot terraBot;

    public Simulation() {
        started = false;
        ended = false;
        eventQueue = new PriorityQueue<Event>();
        terraBot = new TerraBot();
    }
    public Simulation(final SimulationInput simulationInput) {
        this();
        String[] territoryDims = simulationInput.getTerritoryDim().split("x");
        int height = Integer.parseInt(territoryDims[0]);
        int width = Integer.parseInt(territoryDims[1]);
        var territorySectionParamsInput = simulationInput.getTerritorySectionParams();
        environmentMap = new EnvironmentMap(height, width);
        environmentMap.placeEntities(territorySectionParamsInput);
        territorySectionParams = new TerritorySectionParams(
                environmentMap.getMap(), height, width);
        int energyPoints = simulationInput.getEnergyPoints();
        terraBot = new TerraBot(energyPoints, environmentMap.cellAt(0, 0));
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
