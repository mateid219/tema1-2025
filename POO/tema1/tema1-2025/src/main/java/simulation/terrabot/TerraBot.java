package simulation.terrabot;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exceptions.BatteryException;
import exceptions.FactNotSavedException;
import exceptions.ObjectNotFoundException;
import exceptions.SubjectNotSavedException;
import exceptions.UnknownImprovementException;
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
    public TerraBot(final int energyPoints, final Cell cell) {
        this();
        battery = new Battery(energyPoints);
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
        Cell nextCell = map.robotNextCell(cell);
        int nextCellQuality = nextCell.calculateCellQuality();
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
        battery.processUseRequest(SCAN_ENERGY_COST);
        int timestamp = scanParams.getTimestamp();
        ScanResult scanResult = scanner.scan(scanParams, cell);
        battery.drain(SCAN_ENERGY_COST);
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
        battery.processUseRequest(LEARN_ENERGY_COST);
        String components = fact.getComponents();
        inventory.validateRequest(components);
        battery.drain(LEARN_ENERGY_COST);
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
        battery.processUseRequest(IMPROVE_ENVIRONMENT_COST);
        String name = recipe.getName();
        String type = recipe.getImprovementType();
        inventory.validateRequest(name);
        database.validateRequest(name, type);
        inventory.remove(name);
        battery.drain(IMPROVE_ENVIRONMENT_COST);
        return String.format(ImprovementApplier.improveEnvironment(type, cell), name);
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
