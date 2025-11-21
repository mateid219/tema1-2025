package entities.water;

import entities.Food;
import entities.QualitativeEntity;
import fileio.WaterInput;
import lombok.Getter;
import simulation.environmentMap.Cell;
import simulation.terraBot.scanner.ScanParamsVisitor;
import simulation.terraBot.scanner.ScanResult;

import static java.lang.Math.abs;

public final class Water extends QualitativeEntity implements Food {
    private final double salinity;
    private final double pH;
    private final double purity;
    private final double turbidity;
    private final double contaminantIndex;
    private final boolean isFrozen;
    @Getter
    private boolean scanned;
    @Getter
    private int scanTime;

    private static final double PURITY_FACTOR = 100.0;
    private static final double PH_FACTOR = 7.5;
    private static final double SALINITY_FACTOR = 350.0;
    private static final double TURBIDITY_FACTOR = 100.0;
    private static final double CONTAMINANT_FACTOR = 100.0;

    private static final double PURITY_COFF = 0.3;
    private static final double PH_COFF = 0.2;
    private static final double SALINITY_COFF = 0.15;
    private static final double TURBIDITY_COFF = 0.1;
    private static final double CONTAMINANT_COFF = 0.15;
    private static final double FROZEN_COFF = 0.15;

    private static final String OUTPUT_CATEGORY = "water";

    @Override
    public String getPropertyName() {
        return OUTPUT_CATEGORY;
    }

    public Water(final WaterInput waterInput) {
        super(waterInput);
        salinity = waterInput.getSalinity();
        pH = waterInput.getPH();
        purity = waterInput.getPurity();
        turbidity = waterInput.getTurbidity();
        contaminantIndex = waterInput.getContaminantIndex();
        isFrozen = waterInput.isFrozen();
    }

    @Override
    public ScanResult accept(final ScanParamsVisitor visitor,
                             final int timestamp, final Cell cell) {
        scanned = true;
        scanTime = timestamp;
        return visitor.visitWater(this, timestamp, cell);
    }

    @Override
    public double calculateScore() {
        double purityScore = (purity / PURITY_FACTOR) * PURITY_COFF;
        double pHScore = (1.0 - abs(pH - PH_FACTOR) / PH_FACTOR) * PH_COFF;
        double salinityScore = (1.0 - salinity / SALINITY_FACTOR) * SALINITY_COFF;
        double turbidityScore = (1.0 - turbidity / TURBIDITY_FACTOR) * TURBIDITY_COFF;
        double contaminantScore = (1.0 - contaminantIndex / CONTAMINANT_FACTOR) * CONTAMINANT_COFF;
        double frozenScore = (isFrozen ? 0.0 : 1.0) * FROZEN_COFF;
        return (purityScore + pHScore + salinityScore + turbidityScore
                + contaminantScore + frozenScore) * MAX_PERCENTAGE;
    }
    /**
     * Drains the water by specified amount.
     *
     * @param massToBeDrank the water to be drained
     */
    public double beDrank(final double massToBeDrank) {
        double waterDrank = Math.min(massToBeDrank, mass);
        mass -= waterDrank;
        return waterDrank;
    }
}
