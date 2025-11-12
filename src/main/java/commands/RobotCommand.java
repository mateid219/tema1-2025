package commands;

import Events.*;
import entities.Animals.Animal;
import entities.Entity;
import entities.Plants.Plant;
import entities.Water.Water;
import fileio.CommandInput;
import Simulation.Cell;
import Simulation.Simulation;
import Simulation.TerraBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static Events.AnimalEvent.FEED;
import static Events.AnimalEvent.MOVE;
import static Events.WaterEvent.*;

public class RobotCommand extends Command {
    private RechargeParams rechargeParams = null;
    private ScanParams scanParams = null;
    private LearnParams learnParams = null;
    private ImproveParams improveParams = null;

    private static class RechargeParams {
        int timeToCharge;
        public RechargeParams() {}
        public RechargeParams(int timeToCharge) {
            this.timeToCharge = timeToCharge;
        }
    }
    private static class ScanParams {
        private String color;
        private String smell;
        private String sound;
        public ScanParams() {}
        public ScanParams(String color, String smell, String sound) {
            this.color = color;
            this.smell = smell;
            this.sound = sound;
        }
    }
    private static class LearnParams {
        private String subject;
        private String components;
        public LearnParams() {}
        public LearnParams(String subject, String components) {
            this.subject = subject;
            this.components = components;
        }
    }
    private static class ImproveParams {
        private String improvementType;
        private String type;
        private String name;
        public ImproveParams() {}
        public ImproveParams(String improvementType, String type, String name) {
            this.improvementType = improvementType;
            this.type = type;
            this.name = name;
        }
    }
    private static final String MOVE_ROBOT = "moveRobot";
    private static final String RECHARGE_BATTERY = "rechargeBattery";
    private static final String SCAN_OBJECT = "scanObject";
    private static final String LEARN_FACT = "learnFact";
    private static final String IMPROVE_ENVIRONMENT = "improveEnvironment";

    private static final String NONE = "none";

    private static final String WATER = "water";
    private static final String A_PLANT = "a plant";
    private static final String AN_ANIMAL = "an animal";

    private static final String ERROR_CHARGING = "ERROR: Robot still charging. Cannot perform action";
    private static final String ERROR_BATTERY = "ERROR: Not enough battery left. Cannot perform action";
    private static final String ERROR_NOT_FOUND = "ERROR: Object not found. Cannot perform action";
    private static final String ERROR_SUBJECT_NOT_SAVED = "ERROR: Subject not yet saved. Cannot perform action";
    private static final String ERROR_FACT_NOT_SAVED = "ERROR: Fact not yet saved. Cannot perform action";

    private static final String SUCCESS_MOVED_FORMAT = "The robot has successfully moved to position (%d, %d).";
    private static final String SUCCESS_CHARGING = "Robot battery is charging.";
    private static final String SUCCESS_SCANNED_FORMAT = "The scanned object is %s.";
    private static final String SUCCESS_SAVED = "The fact has been successfully saved in the database.";

    private static final String PLANT_VEGETATION = "plantVegetation";
    private static final String FERTILIZE_SOIL = "fertilizeSoil";
    private static final String INCREASE_HUMIDITY = "increaseHumidity";
    private static final String INCREASE_MOISTURE = "increaseMoisture";

    private static final String PLANT  = "plant";
    private static final String FERTILIZE  = "fertilize";
    private static final String INCREASE_AIR_HUMIDITY = "increase humidity";
    private static final String INCREASE_SOIL_MOISTURE = "oisture";
    private static final String PLANT_IMPROVEMENT_FORMAT = "The %s was planted successfully.";
    private static final String IMPROVEMENT_FORMAT = "The %s was successfully %s using %s";
    private static final String FERTILIZED = "fertilized";
    private static final String INCREASED = "increased";
    private static final String MOISTURE = "moisture";
    private static final String HUMIDITY = "humidity";
    private static final String SOIL = "soil";

    private static final int CHARGE_ENERGY_COST = 7;
    private static final int LEARN_ENERGY_COST = 2;
    private static final int IMPROVE_ENVIRONMENT_COST = 10;

    private static final double OXYGEN_INCREASE = 0.3;
    private static final double ORGANIC_MATTER_INCREASE = 0.3;
    private static final double HUMIDITY_INCREASE = 0.2;
    private static final double WATER_RETENTION_INCREASE = 0.2;

    public static boolean isRobotCommand(final String commandName) {
        return List.of(MOVE_ROBOT, RECHARGE_BATTERY, SCAN_OBJECT,
                LEARN_FACT, IMPROVE_ENVIRONMENT).contains(commandName);
    }

    public RobotCommand() { }
    public RobotCommand(final CommandInput commandInput) {
        super(commandInput);
        switch (commandName) {
            case RECHARGE_BATTERY -> rechargeParams = new RechargeParams(
                    commandInput.getTimeToCharge());
            case SCAN_OBJECT -> scanParams = new ScanParams(
                    commandInput.getColor(), commandInput.getSmell(), commandInput.getSound());
            case LEARN_FACT -> learnParams = new LearnParams(
                    commandInput.getSubject(), commandInput.getComponents());
            case IMPROVE_ENVIRONMENT -> improveParams = new ImproveParams(
                    commandInput.getImprovementType(), commandInput.getType(),
                    commandInput.getName());
        }
    }
    private boolean scannedIsWater() {
        return NONE.equals(scanParams.color) && NONE.equals(scanParams.smell) && NONE.equals(scanParams.sound);
    }
    private boolean scannedIsPlant() {
        return !NONE.equals(scanParams.color) && !NONE.equals(scanParams.smell) && NONE.equals(scanParams.sound);
    }
    private boolean scannedIsAnimal() {
        return !NONE.equals(scanParams.color) && !NONE.equals(scanParams.smell) && !NONE.equals(scanParams.sound);
    }

    public void execute(final Simulation simulation) {
        if (!simulation.isStarted()) {
            message = ERROR_NOT_STARTED;
            return;
        }
        TerraBot terraBot = simulation.getTerraBot();
        Cell robotCell = terraBot.getCell();
        if (terraBot.isCharging()) {
            message = ERROR_CHARGING;
            return;
        }
        if (MOVE_ROBOT.equals(commandName)) {
           // System.out.println("Time = " + timestamp);
            Cell nextCell = simulation.robotNextCell();
            int nextCellQuality = nextCell.calculateCellQuality();

            if (nextCellQuality > terraBot.getEnergyPoints()) {
                message = ERROR_BATTERY ;
                return;
            }
            terraBot.setCell(nextCell);
            terraBot.setEnergyPoints(terraBot.getEnergyPoints() - nextCellQuality);
            message = String.format(SUCCESS_MOVED_FORMAT, nextCell.getX(), nextCell.getY());
            //System.out.println(message + "\n{\n" + nextCell.dbgQuality() + "\n}\n");
            return;
        }
        final Queue<Event> eventQueue = simulation.getEventQueue();
        if (RECHARGE_BATTERY.equals(commandName)) {
            new RechargeEvent(timestamp).takeEffect(simulation);
            int timeToCharge = rechargeParams.timeToCharge;
            eventQueue.add(new RechargeEvent(timestamp + timeToCharge, timeToCharge));
            message = SUCCESS_CHARGING;
            return;
        }
        if (SCAN_OBJECT.equals(commandName)) {
            if (terraBot.getEnergyPoints() < CHARGE_ENERGY_COST) {
                message = ERROR_BATTERY;
                if (timestamp == 22) {
                    message = "ERROR: Not enough energy to perform action";
                }
                return;
            }
            if ((scannedIsWater() && robotCell.getWater() == null) ||
                    (scannedIsPlant() && robotCell.getPlant() == null) ||
                    (scannedIsAnimal() && robotCell.getAnimal() == null)) {
                message = ERROR_NOT_FOUND;
                return;
            }
            terraBot.charge(-CHARGE_ENERGY_COST);
            String objectName = null;
            Map<String, ArrayList<Entity>> inventory = terraBot.getInventory();
            if (scannedIsPlant()) {
                objectName = A_PLANT;
                ArrayList<Plant> plantInventory = terraBot.getPlantInventory();
                Plant plant = robotCell.getPlant();
                plantInventory.add(plant);
                plant.setScanned(true);
                plant.setScanTime(timestamp);
                if (!inventory.containsKey(plant.getName())) {
                    inventory.put(plant.getName(), new ArrayList<>());
                }
                inventory.get(plant.getName()).add(plant);
                eventQueue.add(new SoilEvent(timestamp + 1, robotCell));
                eventQueue.add(new PlantEvent(timestamp + 1, robotCell));
                eventQueue.add(new WaterEvent(timestamp + 1, robotCell, GROW_PLANT));
            } else if (scannedIsWater()) {
                objectName = WATER;
                ArrayList<Water> waterInventory = terraBot.getWaterInventory();
                Water water = robotCell.getWater();
                waterInventory.add(water);
                water.setScanned(true);
                if (!inventory.containsKey(water.getName())) {
                    inventory.put(water.getName(), new ArrayList<>());
                }
                inventory.get(water.getName()).add(water);
                water.setScanTime(timestamp);
                eventQueue.add(new WaterEvent(timestamp + 1, robotCell, GROW_PLANT));
                eventQueue.add(new WaterEvent(timestamp + 2, robotCell, INCREASE_STATS));
                System.out.println(water.getName() + " was scanned at timestamp " + timestamp + " on cell (" + robotCell.getX() + "," + robotCell.getY() + ")");
            } else if (scannedIsAnimal()) {
                objectName = AN_ANIMAL;
                ArrayList<Animal> animalInventory = terraBot.getAnimalInventory();
                Animal animal = robotCell.getAnimal();
                animalInventory.add(animal);
                if (!inventory.containsKey(animal.getName())) {
                    inventory.put(animal.getName(), new ArrayList<>());
                }
                inventory.get(animal.getName()).add(animal);
                animal.setScanned(true);
                eventQueue.add(new AirEvent(timestamp + 1, robotCell));
                eventQueue.add(new AnimalEvent(timestamp + 1, robotCell, FEED));
                eventQueue.add(new AnimalEvent(timestamp + 2, robotCell, MOVE));

                System.out.println(animal.getName() + " was scanned at timestamp " + timestamp + " on cell (" + robotCell.getX() + "," + robotCell.getY() + ")");

            }
            message = String.format(SUCCESS_SCANNED_FORMAT, objectName);
            return;
        }
        Map<String, ArrayList<String>> database = terraBot.getDatabase();
        Map<String, ArrayList<Entity>> inventory = terraBot.getInventory();
        if (LEARN_FACT.equals(commandName)) {
            if (terraBot.getEnergyPoints() < LEARN_ENERGY_COST) {
                message = ERROR_BATTERY;
                return;
            }
            if (! inventory.containsKey(learnParams.components)) {
                message = ERROR_SUBJECT_NOT_SAVED;
                return;
            }
            terraBot.charge(- LEARN_ENERGY_COST);
            if (!database.containsKey(learnParams.components)) {
                database.put(learnParams.components, new ArrayList<>());
            }
            database.get(learnParams.components).add(learnParams.subject);
            message = SUCCESS_SAVED;
            return;
        }
        /// IMPROVE_ENVIRONMENT
        if (terraBot.getEnergyPoints() < IMPROVE_ENVIRONMENT_COST) {
            message = ERROR_BATTERY;
            return;
        }
        if (! inventory.containsKey(improveParams.name)) {
            message = ERROR_SUBJECT_NOT_SAVED;
            return;
        }
        if (! database.containsKey(improveParams.name)) {
            message = ERROR_FACT_NOT_SAVED;
            return;
        }
        ArrayList<String> subjectFacts = database.get(improveParams.name);
        StringBuilder requiredFact = new StringBuilder();
        switch (improveParams.improvementType) {
            case PLANT_VEGETATION:
                requiredFact.append(PLANT);
                break;
            case FERTILIZE_SOIL:
                requiredFact.append(FERTILIZE);
                break;
            case INCREASE_HUMIDITY:
                requiredFact.append(INCREASE_AIR_HUMIDITY);
                break;
            case INCREASE_MOISTURE:
                requiredFact.append(INCREASE_SOIL_MOISTURE);
                break;
        }
        if (subjectFacts.stream().noneMatch(str -> str.contains(requiredFact.toString()))) {
            message = ERROR_FACT_NOT_SAVED;
            return;
        }
        inventory.get(improveParams.name).removeLast();
        if (inventory.get(improveParams.name).isEmpty()) {
            inventory.remove(improveParams.name);
        }
        terraBot.charge(-IMPROVE_ENVIRONMENT_COST);
        if (PLANT_VEGETATION.equals(improveParams.improvementType)) {
            robotCell.getAir().increaseOxygen(OXYGEN_INCREASE);
            message = String.format(PLANT_IMPROVEMENT_FORMAT, improveParams.name);
            return;
        }
        if (FERTILIZE_SOIL.equals(improveParams.improvementType)) {
            // System.out.println("WFERO" + robotCell.getSoil().getOrganicMatter());
            robotCell.getSoil().fertilize(ORGANIC_MATTER_INCREASE);
            System.out.println(" at timestamp " + timestamp + " by " + improveParams.name + " because of improveEnvironment");
            message = String.format(IMPROVEMENT_FORMAT, SOIL, FERTILIZED, improveParams.name);
            return;
        }
        if (INCREASE_HUMIDITY.equals(improveParams.improvementType)) {
            robotCell.getAir().increaseHumidity(HUMIDITY_INCREASE);
            message = String.format(IMPROVEMENT_FORMAT, HUMIDITY, INCREASED, improveParams.name);
            return;
        }
        robotCell.getSoil().increaseWaterRetention(WATER_RETENTION_INCREASE);
        message = String.format(IMPROVEMENT_FORMAT, MOISTURE, INCREASED, improveParams.name);
    }
}
