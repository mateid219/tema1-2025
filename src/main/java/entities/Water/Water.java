package entities.Water;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.QualitativeEntity;
import entities.Scannable;
import fileio.WaterInput;
import lombok.Getter;
import simulation.environmentMap.Cell;
import simulation.terrabot.scanner.ScanParamsVisitor;
import simulation.terrabot.scanner.ScanResult;

import static java.lang.Math.abs;

public final class Water extends QualitativeEntity implements Scannable {
    @Getter
    private double salinity;
    @Getter
    private double pH;
    @Getter
    private double purity;
    @Getter
    private double turbidity;
    @Getter
    private double contaminantIndex;
    @Getter
    private boolean isFrozen;
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

    public Water() {
    }

    public Water(final WaterInput waterInput) {
        super(waterInput);
        salinity = waterInput.getSalinity();
        pH = waterInput.getPH();
        purity = waterInput.getPurity();
        turbidity = waterInput.getTurbidity();
        contaminantIndex = waterInput.getContaminantIndex();
        isFrozen = waterInput.isFrozen();
        scanned = false;
        scanTime = -1;
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
     * @param mass the water to be drained
     */
    public double beDrankBy(final double entityMass, final double INTAKE_RATE) {
        double waterMass = Math.round(mass * MAX_PERCENTAGE) / MAX_PERCENTAGE;
        double waterDrank = Math.min(entityMass * INTAKE_RATE, waterMass);
        mass -= waterDrank;
        return waterDrank;
    }


    @Override
    public ObjectNode buildEntityOutput() {
        return super.buildEntityOutput();
    }
}
