package entities.Plants;

import entities.CellQualityAgent;
import entities.Entity;
import entities.Food;
import entities.air.Air;
import fileio.PlantInput;
import lombok.Getter;
import simulation.environmentMap.Cell;
import simulation.terrabot.scanner.ScanParamsVisitor;
import simulation.terrabot.scanner.ScanResult;

public abstract class Plant extends Entity implements Food, CellQualityAgent {
    @Getter private LifeStages maturity;
    @Getter private double growthLevel;
    @Getter private boolean scanned;
    @Getter private int scanTime;

    private static final String OUTPUT_CATEGORY = "plants";

    private static final double MAX_GROWTH = 1.0;

    @Override
    public final String getPropertyName() {
        return OUTPUT_CATEGORY;
    }

    abstract double getStuckProbability();
    abstract double getBaseOxygenLevel();

    public Plant(final PlantInput plantInput) {
        name = plantInput.getName();
        type = plantInput.getType();
        mass = plantInput.getMass();
        maturity = LifeStages.YOUNG;
    }
    @Override
    public final ScanResult accept(final ScanParamsVisitor visitor,
                             final int timestamp, final Cell cell) {
        scanned = true;
        scanTime = timestamp;
        return visitor.visitPlant(this, timestamp, cell);
    }

    /**
     * Sets this plant's state to {@link LifeStages#DEAD} before being eaten.
     * @return this plant's mass
     */
    public final double beEaten() {
        maturity = LifeStages.DEAD;
        return mass;
    }

    /**
     * Check if this plant is dead.
     * @return true if the plant has died.
     */
    public final boolean hasDied() {
        return maturity.equals(LifeStages.DEAD);
    }

    /**
     * Performs this plant's growth process.
     * Sets {@link #maturity} to next {@link LifeStages} element when
     * {@link #growthLevel} reaches {@link #MAX_GROWTH}.
     * @param growthFactor the value to be added to {@link #growthLevel}
     */
    public final void grow(final double growthFactor) {
        growthLevel += growthFactor;
        if (growthLevel < MAX_GROWTH) {
            return;
        }
        growthLevel -= MAX_GROWTH;
        maturity = switch (maturity) {
            case LifeStages.YOUNG -> LifeStages.MATURE;
            case LifeStages.MATURE -> LifeStages.OLD;
            default -> LifeStages.DEAD;
        };
    }

    /**
     * Decides the amount of oxygen produced as a function of {@link #maturity}:
     * <ul>
     *     <li>{@link LifeStages#YOUNG}</li>
     *     <li>{@link LifeStages#MATURE}</li>
     *     <li>{@link LifeStages#OLD}</li>
     *     <li>{@link LifeStages#DEAD}</li>
     * </ul>
     * @return the oxygen rate
     */
    private double maturityOxygenRate() {
        return switch (maturity) {
            case LifeStages.YOUNG ->
                    LifeStages.YOUNG.getOxygenRate();
            case LifeStages.MATURE ->
                    LifeStages.MATURE.getOxygenRate();
            case LifeStages.OLD ->
                    LifeStages.OLD.getOxygenRate();
            default -> LifeStages.DEAD.getOxygenRate();
        };
    }

    /**
     * Calculates the total oxygen this plant generates:
     * <code>{@link #getBaseOxygenLevel()} + {@link #maturityOxygenRate()}</code>
     * @return the total oxygen
     */
    public final double oxygenGenerated() {
        return getBaseOxygenLevel() + maturityOxygenRate();
    }
    private double calculateProbability() {
        return getStuckProbability() / MAX_PERCENTAGE;
    }
    private double possibilityToGetStuckInPlants() {
        return calculateProbability();
    }
    @Override
    public final double cellQualityTerm() {
        return possibilityToGetStuckInPlants();
    }

    /**
     * Performs this plant's automatic interactions.
     * @param cell this plant's cell
     */
    public final void interact(final Cell cell) {
        Air air = cell.getAir();
        air.increaseOxygen(oxygenGenerated());
    }
}
