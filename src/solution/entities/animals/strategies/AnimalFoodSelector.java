package entities.animals.strategies;

import entities.Food;

import java.util.Comparator;

public final class AnimalFoodSelector implements Comparator<Food> {
    private int calculateFoodScore(final Food food) {
        if (food == null) {
            return 0;
        }
        return food.getScanTime();
    }
    @Override
    public int compare(final Food a, final Food b) {
        int aScore = calculateFoodScore(a);
        int bScore = calculateFoodScore(b);
        return Integer.compare(aScore, bScore);
    }
}
