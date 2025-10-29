package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.CommandArrayList;
import fileio.InputLoader;
import fileio.SimulationInput;
import my.Simulation;
import my.SimulationArrayList;

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
     *
     * @param inputPath
     * @param outputPath
     * @throws IOException
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();
        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         *
         * ObjectNode objectNode = MAPPER.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = MAPPER.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */
        // int timeStamp = 0;
        ArrayList<SimulationInput> simulationInputArrayList = inputLoader.getSimulations();
        SimulationArrayList simulationArrayList = new SimulationArrayList(simulationInputArrayList);
        CommandArrayList commandArrayList = new CommandArrayList(inputLoader.getCommands());
        ArrayList<Command> commands = commandArrayList.getCommands();
        Simulation simulation = simulationArrayList.getSimulations().getFirst();

        ObjectNode debugNode = MAPPER.createObjectNode();
        int simulationIndex = 0;
        for (Command command : commands) {
            simulation = simulationArrayList.getSimulations().get(simulationIndex);
            command.execute(simulation);
            if (simulation.isEnded()) {
                ++simulationIndex;
            }
            ObjectNode objectNode = command.buildCommandOutput();
            output.add(objectNode);
        }
       // output.add(debugNode);

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
