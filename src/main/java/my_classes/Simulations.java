package my_classes;

import fileio.SimulationInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Simulations {
    @Getter @Setter private ArrayList<Simulation> simulations;

    public Simulations() {
        simulations = new ArrayList<Simulation>();
    }
    public Simulations(ArrayList<SimulationInput> simulationInputs) {
        this();
        for (SimulationInput simulationInput : simulationInputs) {
            Simulation simulation = new Simulation(simulationInput);
            simulations.add(simulation);
        }
    }
}
