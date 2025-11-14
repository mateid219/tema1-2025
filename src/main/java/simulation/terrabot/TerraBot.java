package simulation.terrabot;

import com.fasterxml.jackson.databind.node.ArrayNode;
import exceptions.*;
import lombok.Getter;
import simulation.Cell;
import simulation.EnvironmentMap;
import simulation.terrabot.improvements.ImprovementApplier;
import simulation.terrabot.improvements.ImprovementRecipe;
import simulation.terrabot.scanner.ScanParams;
import simulation.terrabot.scanner.ScanResult;
import simulation.terrabot.scanner.Scanner;

public final class TerraBot {

    private static final int IMPROVE_ENVIRONMENT_COST = 10;
    private static final int SCAN_ENERGY_COST = 7;
    private static final int LEARN_ENERGY_COST = 2;

    @Getter
    private Cell cell;
    private Battery battery;
    private final Scanner scanner;
    private final Inventory inventory;
    private final Database database;

    public TerraBot() {
        scanner = new Scanner();
        inventory = new Inventory();
        database = new Database();
    }
    public TerraBot(final int energyPoints, Cell cell) {
        this();
        battery = new Battery(energyPoints);
        this.cell = cell;
    }
    public boolean isCharging() {
        return battery.isCharging();
    }
    public void beginCharging() {
        battery.beginCharging();
    }
    public void finishCharging(int chargeTime) {
        battery.finishCharging(chargeTime);
    }
    public int getEnergyPoints() {
        return battery.getEnergyPoints();
    }

    public void move(EnvironmentMap map)
            throws BatteryException {
        Cell nextCell = map.robotNextCell(cell);
        int nextCellQuality = nextCell.calculateCellQuality();
        battery.processUseRequest(nextCellQuality);
        cell = nextCell;
        battery.drain(nextCellQuality);
    }
    public ScanResult scan(ScanParams scanParams)
            throws BatteryException, ObjectNotFoundException {
        battery.processUseRequest(SCAN_ENERGY_COST);
        int timestamp = scanParams.getTimestamp();
        ScanResult scanResult = scanner.scan(scanParams, cell, timestamp);
        battery.drain(SCAN_ENERGY_COST);
        inventory.add(scanResult.getName(), scanResult.getEntity());
        return scanResult;
    }
    public void learnFact(Fact fact)
            throws BatteryException, SubjectNotSavedException {
        battery.processUseRequest(LEARN_ENERGY_COST);
        String components = fact.getComponents();
        inventory.validateRequest(components);
        battery.drain(LEARN_ENERGY_COST);
        database.add(fact);
    }
    public String improveEnvironment(ImprovementRecipe recipe)
            throws BatteryException, SubjectNotSavedException, FactNotSavedException,
                   UnknownImprovementException  {
        battery.processUseRequest(IMPROVE_ENVIRONMENT_COST);
        String name = recipe.getName();
        String type = recipe.getImprovementType();
        inventory.validateRequest(name);
        database.validateRequest(name, type);
        inventory.remove(name);
        battery.drain(IMPROVE_ENVIRONMENT_COST);
        return String.format(ImprovementApplier.improveEnvironment(type, cell), name);
    }

    public ArrayNode printKnowledgeBase() {
        return database.print();
    }
}
