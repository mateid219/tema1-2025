package simulation.events.weather;

import entities.air.DesertAir;
import entities.air.MountainAir;
import entities.air.PolarAir;
import entities.air.TemperateAir;
import entities.air.TropicalAir;

public interface WeatherChangeVisitor {
    /**
     * Classes that change {@link DesertAir} weather should override this.
     * After applying changes, this must return true.
     * @param air the desert air
     * @return true if any changes were made to the air.
     */
    default boolean visitDesertAir(final DesertAir air) {
        return false;
    }
    /**
     * Classes that change {@link MountainAir} weather should override this.
     * After applying changes, this must return true.
     * @param air the mountain air
     * @return true if any changes were made to the air.
     */
    default boolean visitMountainAir(final MountainAir air) {
        return false;
    }
    /**
     * Classes that change {@link TropicalAir} weather should override this.
     * After applying changes, this must return true.
     * @param air the tropical air
     * @return true if any changes were made to the air.
     */
    default boolean visitTropicalAir(final TropicalAir air) {
        return false;
    }
    /**
     * Classes that change {@link TemperateAir} weather should override this.
     * After applying changes, this must return true.
     * @param air the temperate air
     * @return true if any changes were made to the air.
     */
    default boolean visitTemperateAir(final TemperateAir air) {
        return false;
    }
    /**
     * Classes that change {@link PolarAir} weather should override this.
     * After applying changes, this must return true.
     * @param air the polar air
     * @return true if any changes were made to the air.
     */
    default boolean visitPolarAir(final PolarAir air) {
        return false;
    }
}
