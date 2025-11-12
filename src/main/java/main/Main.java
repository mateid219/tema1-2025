package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import fileio.CommandInput;
import fileio.InputLoader;
import fileio.SimulationInput;
import Simulation.Simulation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {

    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * @param inputPath input file path
     * @param outputPath output file path
     * @throws IOException when files cannot be loaded.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();
        ArrayList<SimulationInput> simulationInputArrayList = inputLoader.getSimulations();
        ArrayList<Simulation> simulationArrayList = new ArrayList<>();
        for (SimulationInput simulationInput : simulationInputArrayList) {
            simulationArrayList.add(new Simulation(simulationInput));
        }
        simulationArrayList.add(new Simulation());
        ArrayList<CommandInput> commandInputArrayList = inputLoader.getCommands();
        ArrayList<Command> commandArrayList = new ArrayList<>();
        for (CommandInput commandInput : commandInputArrayList) {
            commandArrayList.add(Command.createCommand(commandInput));
        }
        Simulation simulation = simulationArrayList.getFirst();
        ObjectNode debugNode = MAPPER.createObjectNode();
        int simulationIndex = 0;
        for (Command command : commandArrayList) {
            command.executeInSync(simulation);
            if (simulation.isEnded()) {
                ++simulationIndex;
                simulation = simulationArrayList.get(simulationIndex);
            }
            ObjectNode objectNode = command.buildCommandOutput();
            output.add(objectNode);
        }
        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
