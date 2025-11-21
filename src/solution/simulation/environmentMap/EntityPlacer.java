package simulation.environmentMap;

import entities.animals.AnimalFactory;
import entities.plants.PlantFactory;
import entities.soil.SoilFactory;
import entities.water.WaterFactory;
import entities.air.AirFactory;
import fileio.AirInput;
import fileio.AnimalInput;
import fileio.EntityInput;
import fileio.PairInput;
import fileio.PlantInput;
import fileio.SoilInput;
import fileio.WaterInput;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class EntityPlacer {
    private EntityPlacer() throws AssertionError {
        throw new AssertionError(
                "Utility classes should not be instantiated.");
    }
    private static <T extends EntityInput, E> void placeEntities(
            final List<T> inputs, final Cell[][] map, final Function<T, E> entityCreator,
                    final BiConsumer<Cell, E> entitySetter) {
        for (T input : inputs) {
            for (PairInput pairInput : input.getSections()) {
                E createdEntity = entityCreator.apply(input);
                entitySetter.accept(map[pairInput.getX()][pairInput.getY()], createdEntity);
            }
        }
    }

    /**
     * Calls placeEntities for Soil entities
     */
    public static void placeSoil(final List<SoilInput> soilInputs, final Cell[][] map) {
        placeEntities(soilInputs, map, SoilFactory::createSoil, Cell::setSoil);
    }
    /**
     * Calls placeEntities for Air entities
     */
    public static void placeAir(final List<AirInput> airInputs, final Cell[][] map) {
        placeEntities(airInputs, map, AirFactory::createAir, Cell::setAir);
    }
    /**
     * Calls placeEntities for Water entities
     */
    public static void placeWater(final List<WaterInput> waterInputs, final Cell[][] map) {
        placeEntities(waterInputs, map, WaterFactory::createWater, Cell::setWater);
    }
    /**
     * Calls placeEntities for Plant entities
     */
    public static void placePlants(final List<PlantInput> plantInputs, final Cell[][] map) {
        placeEntities(plantInputs, map, PlantFactory::createPlant, Cell::setPlant);
    }
    /**
     * Calls placeEntities for Animal entities
     */
    public static void placeAnimals(final List<AnimalInput> animalInputs, final Cell[][] map) {
        placeEntities(animalInputs, map, AnimalFactory::createAnimal, Cell::setAnimal);
    }
}
