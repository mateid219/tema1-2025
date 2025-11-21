package simulation.events.weather;

import entities.air.Desert;
import entities.air.Mountain;
import entities.air.Polar;
import entities.air.Temperate;
import entities.air.Tropical;

public interface WeatherChangeVisitor {
    /**
     * Classes that change {@link Desert} weather should override this.
     * After applying changes, this must return true.
     * @param air the desert air
     * @return true if any changes were made to the air.
     */
    default boolean visitDesertAir(final Desert air) {
        return false;
    }
    /**
     * Classes that change {@link Mountain} weather should override this.
     * After applying changes, this must return true.
     * @param air the mountain air
     * @return true if any changes were made to the air.
     */
    default boolean visitMountainAir(final Mountain air) {
        return false;
    }
    /**
     * Classes that change {@link Tropical} weather should override this.
     * After applying changes, this must return true.
     * @param air the tropical air
     * @return true if any changes were made to the air.
     */
    default boolean visitTropicalAir(final Tropical air) {
        return false;
    }
    /**
     * Classes that change {@link Temperate} weather should override this.
     * After applying changes, this must return true.
     * @param air the temperate air
     * @return true if any changes were made to the air.
     */
    default boolean visitTemperateAir(final Temperate air) {
        return false;
    }
    /**
     * Classes that change {@link Polar} weather should override this.
     * After applying changes, this must return true.
     * @param air the polar air
     * @return true if any changes were made to the air.
     */
    default boolean visitPolarAir(final Polar air) {
        return false;
    }
}
