package Simulation;

import fileio.InputLoader;
import fileio.SimulationInput;

import java.util.ArrayList;

public final class InputParser {


    static public ArrayList<Simulation> extractSimulations(InputLoader inputLoader) {
        ArrayList<SimulationInput> simulationInputArrayList = inputLoader.getSimulations();
        ArrayList<Simulation> simulations = new ArrayList<>();
        for (SimulationInput simulationInput : simulationInputArrayList) {
            Simulation simulation = new Simulation(simulationInput);
            simulations.add(simulation);
        }
        simulations.add(new Simulation());
        return simulations;
    }

}
