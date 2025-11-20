package simulation.terrabot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exceptions.FactNotSavedException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Database {
    private final Map<String, ArrayList<String>> database;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public Database() {
        database = new LinkedHashMap<>();
    }

    /**
     * Adds a fact to the database.
     */
    public void add(final Fact fact) {
        String subject = fact.getSubject();
        String components = fact.getComponents();
        if (!database.containsKey(components)) {
            database.put(components, new ArrayList<>());
        }
        database.get(components).add(subject);
    }

    /**
     * Searches for an (object, fact) pair in the database.
     * @param components the object searched
     * @param factText the fact searched
     * @throws FactNotSavedException if the fact is not in the database.
     */
    public void validateRequest(final String components, final String factText)
            throws FactNotSavedException {
        if (!database.containsKey(components)) {
            throw new FactNotSavedException();
        }
        if (database.get(components).stream().noneMatch(
                str -> str.contains(factText.split("(?=[A-Z])", 2)[0]))) {
            throw new FactNotSavedException();
        }
    }

    /**
     * Builds the database output required for printKnowledgeBase.
     * @return an arrayNode containing the information in the database.
     */
    public ArrayNode print() {
        ArrayNode output = MAPPER.createArrayNode();
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
