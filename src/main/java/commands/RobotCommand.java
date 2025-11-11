package commands;

import Events.*;
import entities.Animals.Animal;
import entities.Plants.Plant;
import entities.Water.Water;
import fileio.CommandInput;
import Simulation.Cell;
import Simulation.Simulation;
import Simulation.TerraBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static Events.AnimalEvent.FEED;
import static Events.AnimalEvent.MOVE;
import static Events.WaterEvent.*;

public class RobotCommand extends Command {
    private int timeToCharge;
    private String color;
    private String smell;
    private String sound;

    private static final String MOVE_ROBOT = "moveRobot";
    private static final String RECHARGE_BATTERY = "rechargeBattery";
    private static final String SCAN_OBJECT = "scanObject";


    private static final String NONE = "none";
    private static final String PINK = "pink";
    private static final String BROWN = "brown";
    private static final String SWEET = "sweet";
    private static final String EARTHY = "earthy";
    private static final String MUU = "muu";

    private static final String WATER = "water";
    private static final String PLANT = "a plant";
    private static final String ANIMAL = "an animal";

    private static final String ERROR_CHARGING = "ERROR: Robot still charging. Cannot perform action";
    private static final String ERROR_BATTERY = "ERROR: Not enough battery left. Cannot perform action";
    private static final String ERROR_NOT_FOUND = "ERROR: Object not found. Cannot perform action";

    private static final String SUCCESS_MOVED_FORMAT = "The robot has successfully moved to position (%d, %d).";
    private static final String SUCCESS_CHARGING = "Robot battery is charging.";
    private static final String SUCCESS_SCANNED_FORMAT = "The scanned object is %s.";

    private static final int CHARGE_ENERGY_COST = 7;

    static final String[] RBT_COMMANDS = {
            "learnFact",
            "improveEnvironment"
    };
    public static boolean isRobotCommand(final String commandName) {
        return List.of(MOVE_ROBOT, RECHARGE_BATTERY, SCAN_OBJECT).contains(commandName);
    }

    public RobotCommand() { }
    public RobotCommand(final CommandInput commandInput) {
        super(commandInput);
        timeToCharge = commandInput.getTimeToCharge();
        if (SCAN_OBJECT.equals(commandName)) {
            color = commandInput.getColor();
            smell = commandInput.getSmell();
            sound = commandInput.getSound();
        }
    }
    private boolean scannedIsWater() {
        return NONE.equals(color) && NONE.equals(smell) && NONE.equals(sound);
    }
    private boolean scannedIsPlant() {
        return PINK.equals(color) && SWEET.equals(smell) && NONE.equals(sound);
    }
    private boolean scannedIsAnimal() {
        return BROWN.equals(color) && EARTHY.equals(smell) && MUU.equals(sound);
    }

    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        TerraBot terraBot = simulation.getTerraBot();
        if (terraBot.isCharging()) {
            message = ERROR_CHARGING;
            return;
        }
        if (MOVE_ROBOT.equals(commandName)) {
            System.out.println("Time = " + timestamp);
            Cell nextCell = simulation.robotNextCell();
            int nextCellQuality = nextCell.calculateCellQuality();

            if (nextCellQuality > terraBot.getEnergyPoints()) {
                message = ERROR_BATTERY ;
                return;
            }
            terraBot.setCell(nextCell);
            terraBot.setEnergyPoints(terraBot.getEnergyPoints() - nextCellQuality);
            message = String.format(SUCCESS_MOVED_FORMAT, nextCell.getX(), nextCell.getY());
            System.out.println(message + "\n{\n" + nextCell.dbgQuality() + "\n}\n");
            return;
        }
        final Queue<Event> eventQueue = simulation.getEventQueue();
        if (RECHARGE_BATTERY.equals(commandName)) {
            new RechargeEvent(timestamp).takeEffect(simulation);
            eventQueue.add(new RechargeEvent(timestamp + timeToCharge, timeToCharge));
            message = SUCCESS_CHARGING;
            return;
        }
        if (SCAN_OBJECT.equals(commandName)) {
            if (terraBot.getEnergyPoints() < CHARGE_ENERGY_COST) {
                message = ERROR_BATTERY;
                return;
            }

            Cell robotCell = terraBot.getCell();
            if ((scannedIsWater() && robotCell.getWater() == null) ||
                    (scannedIsPlant() && robotCell.getPlant() == null) ||
                    (scannedIsAnimal() && robotCell.getAnimal() == null)) {
                message = ERROR_NOT_FOUND;
                return;
            }
            terraBot.charge(-CHARGE_ENERGY_COST);
            String objectName = null;
            if (scannedIsPlant()) {
                objectName = PLANT;
                ArrayList<Plant> plantInventory = terraBot.getPlantInventory();
                Plant plant = robotCell.getPlant();
                plantInventory.add(plant);
                plant.setScanned(true);
                plant.setScanTime(timestamp);
                eventQueue.add(new SoilEvent(timestamp + 1, robotCell));
                eventQueue.add(new PlantEvent(timestamp + 1, robotCell));
                eventQueue.add(new WaterEvent(timestamp + 1, robotCell, GROW_PLANT));
            } else if (scannedIsWater()) {
                objectName = WATER;
                ArrayList<Water> waterInventory = terraBot.getWaterInventory();
                Water water = robotCell.getWater();
                waterInventory.add(water);
                water.setScanned(true);
                water.setScanTime(timestamp);
                eventQueue.add(new WaterEvent(timestamp + 1, robotCell, GROW_PLANT));
                eventQueue.add(new WaterEvent(timestamp + 2, robotCell, INCREASE_STATS));
            } else if (scannedIsAnimal()) {
                objectName = ANIMAL;
                ArrayList<Animal> animalInventory = terraBot.getAnimalInventory();
                Animal animal = robotCell.getAnimal();
                animalInventory.add(animal);
                animal.setScanned(true);
                eventQueue.add(new AirEvent(timestamp + 1, robotCell));
                eventQueue.add(new AnimalEvent(timestamp + 1, robotCell, FEED));
                eventQueue.add(new AnimalEvent(timestamp + 2, robotCell, MOVE));

            }
            message = String.format(SUCCESS_SCANNED_FORMAT, objectName);
            return;
        }


    }
}
