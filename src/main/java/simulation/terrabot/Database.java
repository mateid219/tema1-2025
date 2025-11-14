package simulation.terrabot;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exceptions.FactNotSavedException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static simulation.Simulation.MAPPER;

public final class Database {
    private final Map<String, ArrayList<String>> database;
    public Database() {
        database = new LinkedHashMap<>();
    }
    public void add(Fact fact) {
        String subject = fact.getSubject();
        String components = fact.getComponents();
        if (!database.containsKey(components)) {
            database.put(components, new ArrayList<>());
        }
        database.get(components).add(subject);
    }
    public void validateRequest(String components, String type)
            throws FactNotSavedException {
        if (!database.containsKey(components)) {
            throw new FactNotSavedException();
        }
        if (database.get(components).stream().noneMatch(
                str -> str.contains(type.split("(?=[A-Z])", 2)[0]))) {
            throw new FactNotSavedException();
        }
    }
    public ArrayNode print() {
        ArrayNode output = MAPPER.createArrayNode();
        System.out.println("DATABASE :::::\n" + database);
        for (Map.Entry<String, ArrayList<String>> entry : database.entrySet()) {
            ObjectNode entryNode = MAPPER.createObjectNode();
            entryNode.put("topic", entry.getKey());
            ArrayNode facts = MAPPER.createArrayNode();
            for (String fact : entry.getValue()) {
                facts.add(fact);
            }
            entryNode.set("facts", facts);
            output.add(entryNode);
        }
        return output;
    }
}
