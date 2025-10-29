package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.InputLoader;
import my_classes.Simulation;
import my_classes.Simulations;

import java.io.File;
import java.io.IOException;

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
        Simulations simulations = new Simulations(inputLoader.getSimulations());
        ObjectNode objectNode = MAPPER.createObjectNode();
        Simulation sim = simulations.getSimulations().getFirst();
        int height = sim.getHeight();
        int width = sim.getWidth();
        objectNode.put("width", width);
        objectNode.put("height", "field2");
        ArrayNode arrayNode = MAPPER.createArrayNode();
        arrayNode.add(objectNode);
        output.add(objectNode);

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
