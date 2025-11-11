package entities.Plants;

import entities.Entity;
import fileio.PlantInput;
import lombok.Getter;
import lombok.Setter;

public class Plant extends Entity {
    @Getter private String maturity;
    @Getter private double growthLevel;
    @Getter @Setter private boolean scanned;
    @Getter @Setter private int scanTime;


    enum categories {
        ANGIOSPERMS("FloweringPlants", 90.0, 6.0),
        GYMNOSPERMS("GymnospermsPlants", 60.0, 0.0),
        FERNS("Ferns", 30.0, 0.0),
        MOSSES("Mosses", 40.0, 0.8),
        ALGAE("Algae", 20.0, 0.5);

        private final String category;
        private final double stuckProbability;
        private final double baseOxygenLevel;
        categories(String category, double stuckProbability, double baseOxygenLevel) {
            this.category = category;
            this.stuckProbability = stuckProbability;
            this.baseOxygenLevel = baseOxygenLevel;
        }
        public double calculateProbability() {
            return stuckProbability / MAX_PERCENTAGE;
        }
    }

    private static final String YOUNG = "young";
    private static final String MATURE = "mature";
    private static final String OLD = "old";
    private static final String DEAD = "dead";

    private static final double YOUNG_OXYGEN_RATE = 0.2;
    private static final double MATURE_OXYGEN_RATE = 0.7;
    private static final double OLD_OXYGEN_RATE = 0.4;

    public static Plant createPlant(PlantInput plantInput, int x, int y) {
        return new Plant(plantInput, x, y);
    }
    public Plant() {
        growthLevel = 0.0;
        maturity = YOUNG;
        scanned = false;
        scanTime = -1;
    }
    public Plant(final PlantInput plantInput, int x, int y) {
        this();
        name = plantInput.getName();
        type = plantInput.getType();
        mass = plantInput.getMass();
        this.x = x;
        this.y = y;
        maturity = YOUNG;
    }
    public double possibilityToGetStuckInPlants() {
        for(categories plantType : categories.values()) {
            if (type.equals(plantType.category)) {
                return plantType.calculateProbability();
            }
        }
        return 0.0;
    }
    public void grow(double growthFactor) {
        growthLevel += growthFactor;
        if (growthLevel < 1.0) {
            return;
        }
        growthLevel -= 1.0;
        if (YOUNG.equals(maturity)) {
            maturity = MATURE;
        } else if (MATURE.equals(maturity)) {
            maturity = OLD;
        } else {
            maturity = DEAD;
        }
    }
    private double maturityOxygenRate() {
        if (YOUNG.equals(maturity)) {
            return YOUNG_OXYGEN_RATE;
        }
        if (MATURE.equals(maturity)) {
            return MATURE_OXYGEN_RATE;
        }
        return OLD_OXYGEN_RATE;
    }
    public double oxygenGenerated() {
        double oxygen = 0.0;
        for (categories plantType : categories.values()) {
            if (type.equals(plantType.category)) {
                oxygen = plantType.baseOxygenLevel;
                break;
            }
        }
        return oxygen + maturityOxygenRate();
    }
}
