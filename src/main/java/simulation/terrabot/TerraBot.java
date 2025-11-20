package simulation.terrabot;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exceptions.*;
import lombok.Getter;
import simulation.environmentMap.Cell;
import simulation.environmentMap.EnvironmentMap;
import simulation.terrabot.improvements.ImprovementApplier;
import simulation.terrabot.improvements.ImprovementRecipe;
import simulation.terrabot.scanner.ScanParams;
import simulation.terrabot.scanner.ScanResult;
import simulation.terrabot.scanner.Scanner;

public final class TerraBot {

    @Getter
    private Cell cell;
    private final Battery battery;
    private final Scanner scanner;
    private final Inventory inventory;
    private final Database database;
    private final RobotCellPreference cellPreference;

    public TerraBot(final int energyPoints, final Cell cell) {
        scanner = new Scanner();
        inventory = new Inventory();
        database = new Database();
        battery = new Battery(energyPoints);
        cellPreference = new RobotCellPreference();
        this.cell = cell;
    }

    /**
     * Passes execution of command to {@link #battery}
     */
    public boolean isCharging() {
        return battery.isCharging();
    }
    /**
     * Passes execution of command to {@link #battery}
     */
    public void beginCharging() {
        battery.beginCharging();
    }
    /**
     * Passes execution of command to {@link #battery}
     */
    public void finishCharging(final int chargeTime) {
        battery.finishCharging(chargeTime);
    }
    /**
     * Passes execution of command to {@link #battery}
     */
    public int getEnergyPoints() {
        return battery.getEnergyPoints();
    }

    /**
     * Attempts to move the robot.
     * @throws BatteryException if there is not enough battery left
     */
    public void move(final EnvironmentMap map)
            throws BatteryException {
        Cell nextCell = map.nextCell(cell, cellPreference);
        int nextCellQuality = cellPreference.getCellQuality(nextCell);
        battery.processUseRequest(nextCellQuality);
        cell = nextCell;
        battery.drain(nextCellQuality);
    }

    /**
     * Validates the scan operation and passes it to the scanner.
     * @return the result of the scan
     * @throws BatteryException if there is not enough battery left
     * @throws ObjectNotFoundException if there is no object on the cell of the specified params
     */
    public ScanResult scan(final ScanParams scanParams)
            throws BatteryException, ObjectNotFoundException {
        battery.processUseRequest(EnergyCosts.SCAN);
        ScanResult scanResult = scanner.scan(scanParams, cell);
        battery.drain(EnergyCosts.SCAN);
        inventory.add(scanResult.getName(), scanResult.getEntity());
        return scanResult;
    }

    /**
     * Stores a fact in the inventory.
     * @throws BatteryException if there is not enough battery left
     * @throws SubjectNotSavedException if the object with the fact was not scanned
     */
    public void learnFact(final Fact fact)
            throws BatteryException, SubjectNotSavedException {
        battery.processUseRequest(EnergyCosts.LEARN);
        String components = fact.getComponents();
        inventory.validateRequest(components);
        battery.drain(EnergyCosts.LEARN);
        database.add(fact);
    }

    /**
     * Performs the improveEnvironmentCommand
     * @return the success message
     * @throws BatteryException if there is not enough battery left
     * @throws SubjectNotSavedException if the object with the fact was not scanned
     * @throws FactNotSavedException if the fact was not saved
     * @throws UnknownImprovementException if the improvement does not match the possible ones
     */
    public String improveEnvironment(final ImprovementRecipe recipe)
            throws BatteryException, SubjectNotSavedException, FactNotSavedException,
                   UnknownImprovementException  {
        battery.processUseRequest(EnergyCosts.IMPROVE_ENVIRONMENT);
        String name = recipe.getName();
        String type = recipe.getImprovementType();
        inventory.validateRequest(name);
        database.validateRequest(name, type);
        String improvementMessage = String.format(
                ImprovementApplier.improveEnvironment(type, cell), name);
        inventory.remove(name);
        battery.drain(EnergyCosts.IMPROVE_ENVIRONMENT);
        return improvementMessage;
    }

    /**
     * Passes execution to {@link #cell}
     */
    public ObjectNode printEnvConditions() {
        return cell.buildEnvConditions();
    }

    /**
     * Passes execution to {@link #database}
     */
    public ArrayNode printKnowledgeBase() {
        return database.print();
    }
}
