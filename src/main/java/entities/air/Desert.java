package entities.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import simulation.events.weather.WeatherChangeVisitor;

public final class Desert extends Air {
    private double dustParticles;
    @Getter @Setter private boolean desertStorm;

    private static final double MAX_SCORE = 65.0;
    private static final double OXYGEN_COFF = 2.0;
    private static final double TEMPERATURE_COFF = 0.3;
    private static final double DUST_COFF = 0.2;
    private static final double STORM_SCORE = 30.0;

    public Desert() { }
    public Desert(final AirInput airInput) {
        super(airInput);
        desertStorm = false;
        maxScore = MAX_SCORE;
        dustParticles = airInput.getDustParticles();
    }

    @Override
    public boolean accept(final WeatherChangeVisitor weatherChangeVisitor) {
        return weatherChangeVisitor.visitDesertAir(this);
    }

    @Override
    public double calculateScore() {
        double oxygenScore = oxygenLevel * OXYGEN_COFF;
        double temperatureScore = temperature * TEMPERATURE_COFF;
        double dustScore = dustParticles * DUST_COFF;
        double score = finalScore(oxygenScore - temperatureScore - dustScore);
        return score - (desertStorm ? STORM_SCORE : 0);
    }
    @Override public ObjectNode buildEntityOutput() {
        ObjectNode objectNode = super.buildEntityOutput();
        objectNode.put("desertStorm", desertStorm);
        return objectNode;
    }
}
