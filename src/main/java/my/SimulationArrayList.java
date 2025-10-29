package my;

import fileio.SimulationInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class SimulationArrayList {
    @Getter @Setter private ArrayList<Simulation> simulations;

    public SimulationArrayList() {
        simulations = new ArrayList<Simulation>();
    }
    public SimulationArrayList(final ArrayList<SimulationInput> simulationInputs) {
        this();
        for (SimulationInput simulationInput : simulationInputs) {
            Simulation simulation = new Simulation(simulationInput);
            simulations.add(simulation);
        }
    }
}
