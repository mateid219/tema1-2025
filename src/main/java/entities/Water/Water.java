package entities.Water;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Plants.Plant;
import entities.QualitativeEntity;
import fileio.PairInput;
import fileio.PlantInput;
import fileio.WaterInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public final class Water extends QualitativeEntity {
    @Getter @Setter private double salinity;
    @Getter @Setter private double pH;
    @Getter @Setter private double purity;
    @Getter @Setter private double turbidity;
    @Getter @Setter private double contaminantIndex;
    @Getter @Setter private boolean isFrozen;
    @Getter @Setter private boolean scanned;
    @Getter @Setter private int scanTime;

    private static final double PURITY_FACTOR = 100.0;
    private static final double PH_FACTOR = 7.5;
    private static final double SALINITY_FACTOR = 350.0;
    private static final double TURBIDITY_FACTOR = 100.0;
    private static final double CONTAMINANT_FACTOR = 100.0;

    private static final double PURITY_COEFF = 0.3;
    private static final double PH_COEF = 0.2;
    private static final double SALINITY_COEF = 0.15;
    private static final double TURBIDITY_COEF = 0.1;
    private static final double CONTAMINANT_COEF = 0.15;
    private static final double FROZEN_COEF = 0.15;

    public static Water createWater(WaterInput waterInput, int x, int y) {
        return new Water(waterInput, x, y);
    }
    public Water() {
        scanned = false;
        scanTime = -1;
    }
    public Water(final WaterInput waterInput, int x, int y) {
        this();
        name = waterInput.getName();
        type = waterInput.getType();
        mass = waterInput.getMass();
        this.x = x;
        this.y = y;
        salinity = waterInput.getSalinity();
        pH = waterInput.getPH();
        purity = waterInput.getPurity();
        turbidity = waterInput.getTurbidity();
        contaminantIndex = waterInput.getContaminantIndex();
        isFrozen = waterInput.isFrozen();
        scanned = false;
    }
    /**
     * Calculeaza scorul(fara normalizare) asociat calitatii apei
     * @return score
     */
    public double calculateScore() {
        double purityScore = (purity / PURITY_FACTOR) * PURITY_COEFF;
        double pHScore = (1.0 - abs(pH - PH_FACTOR) / PH_FACTOR) * PH_COEF;
        double salinityScore = (1.0 - salinity / SALINITY_FACTOR) * SALINITY_COEF;
        double turbidityScore = (1.0 - (double) turbidity / TURBIDITY_FACTOR) * TURBIDITY_COEF;
        double contaminantScore = (1.0 - contaminantIndex / CONTAMINANT_FACTOR) * CONTAMINANT_COEF;
        double frozenScore = (isFrozen ? 0.0 : 1.0) * FROZEN_COEF;
        return (purityScore + pHScore + salinityScore + turbidityScore
                + contaminantScore + frozenScore) * MAX_PERCENTAGE;
    }
    @Override public double calculateFinalScore() {
        return calculateScore();
    }
    public void drain(double mass) {
        this.mass -= mass;
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        return objectNode;
        /// update gresit test1
        /*
        objectNode.put("purity", purity);
        objectNode.put("salinity", salinity);
        objectNode.put("turbidity", (double) turbidity);
        objectNode.put("contaminantIndex" , contaminantIndex);
        objectNode.put("pH" , pH);
        objectNode.put("isFrozen" , isFrozen);
        return objectNode;
         */
    }
}
