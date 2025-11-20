package entities.Plants;

import entities.CellQualityAgent;
import entities.Entity;
import entities.Scannable;
import entities.air.Air;
import fileio.PlantInput;
import lombok.Getter;
import simulation.environmentMap.Cell;
import simulation.terrabot.scanner.ScanParamsVisitor;
import simulation.terrabot.scanner.ScanResult;

public abstract class Plant extends Entity implements Scannable, CellQualityAgent {
    @Getter private PlantConstants.LifeStages maturity;
    @Getter private double growthLevel;
    @Getter private boolean scanned;
    @Getter private int scanTime;


    @Override
    public String getPropertyName() {
        return PlantConstants.OUTPUT_CATEGORY;
    }

    abstract double getStuckProbability();
    abstract double getBaseOxygenLevel();

    public Plant(final PlantInput plantInput) {
        name = plantInput.getName();
        type = plantInput.getType();
        mass = plantInput.getMass();
        maturity = PlantConstants.LifeStages.YOUNG;
    }
    public final ScanResult accept(final ScanParamsVisitor visitor,
                             final int timestamp, final Cell cell) {
        scanned = true;
        scanTime = timestamp;
        return visitor.visitPlant(this, timestamp, cell);
    }
    public boolean hasDied() {
        return maturity.equals(PlantConstants.LifeStages.DEAD);
    }
    public final void grow(final double growthFactor) {
        growthLevel += growthFactor;
        if (growthLevel < PlantConstants.MAX_GROWTH) {
            return;
        }
        growthLevel -= PlantConstants.MAX_GROWTH;
        maturity = switch (maturity) {
            case PlantConstants.LifeStages.YOUNG -> PlantConstants.LifeStages.MATURE;
            case PlantConstants.LifeStages.MATURE -> PlantConstants.LifeStages.OLD;
            default -> PlantConstants.LifeStages.DEAD;
        };
    }
    private double maturityOxygenRate() {
        return switch (maturity) {
            case PlantConstants.LifeStages.YOUNG ->
                    PlantConstants.LifeStages.YOUNG.getOxygenRate();
            case PlantConstants.LifeStages.MATURE ->
                    PlantConstants.LifeStages.MATURE.getOxygenRate();
            case PlantConstants.LifeStages.OLD ->
                    PlantConstants.LifeStages.OLD.getOxygenRate();
            default -> PlantConstants.LifeStages.DEAD.getOxygenRate();
        };
    }
    public final double oxygenGenerated() {
        return getBaseOxygenLevel() + maturityOxygenRate();
    }
    public final double calculateProbability() {
        return getStuckProbability() / MAX_PERCENTAGE;
    }
    public final double possibilityToGetStuckInPlants() {
        return calculateProbability();
    }
    @Override
    public final double cellQualityTerm() {
        return possibilityToGetStuckInPlants();
    }
    public void interact(Cell cell) {
        Air air = cell.getAir();
        air.increaseOxygen(oxygenGenerated());
    }
}
