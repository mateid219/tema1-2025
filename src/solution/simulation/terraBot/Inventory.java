package simulation.terraBot;

import entities.Scannable;
import exceptions.SubjectNotSavedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Inventory {
    private final Map<String, ArrayList<Scannable>> inventory;
    public Inventory() {
        inventory = new HashMap<>();
    }

    /**
     * Adds a scannable entity in the inventory.
     * @param name the name of the entity
     * @param entity a scannable entity
     */
    public void add(final String name, final Scannable entity) {
        if (!inventory.containsKey(name)) {
            inventory.put(name, new ArrayList<>());
        }
        inventory.get(name).add(entity);
    }

    /**
     * Removes an entity from the inventory. The removed entity must already
     * be present in the inventory at least once.
     * @param name the name of the entity
     */
    public void remove(final String name) {
        inventory.get(name).removeLast();
        if (inventory.get(name).isEmpty()) {
            inventory.remove(name);
        }
    }

    /**
     * Checks if an entity is in the inventory.
     * @param name the name of the entity
     * @throws SubjectNotSavedException if the queried entity is not in the inventory.
     */
    public void validateRequest(final String name)
            throws SubjectNotSavedException {
        if (!inventory.containsKey(name)) {
            throw new SubjectNotSavedException();
        }
    }
}
