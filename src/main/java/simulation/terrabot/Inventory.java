package simulation.terrabot;

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
    public void add(String name, Scannable entity) {
        if (!inventory.containsKey(name)) {
            inventory.put(name, new ArrayList<>());
        }
        inventory.get(name).add(entity);
    }
    public void remove(String name) {
        inventory.get(name).removeLast();
        if (inventory.get(name).isEmpty()) {
            inventory.remove(name);
        }
    }
    public void validateRequest(String name)
            throws SubjectNotSavedException {
        if (!inventory.containsKey(name)) {
            throw new SubjectNotSavedException();
        }
    }
}
