package Lab1and2;

import java.util.ArrayList;
import java.util.List;
/**
 * En bilverkstad som kan ta emot bilar av en viss typ T.
 * T styrs av generics och kontrolleras vid kompilering (compile-time).
 */
public class Workshop<T extends Car> {
    private final int maxCapacity;
    private final List<T> storedCars;

    public Workshop(int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity måste vara >= 0");
        }
        this.maxCapacity = maxCapacity;
        this.storedCars = new ArrayList<>();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getStoredCount() {
        return storedCars.size();
    }

    /**
     * Lämna in en bil i verkstaden.
     * @return true om bilen togs emot, annars false om verkstaden är full.
     */
    public boolean load(T car) {
        if (storedCars.size() >= maxCapacity) return false;
        return storedCars.add(car);
    }

    /**
     * Hämta ut en bil från verkstaden.
     * @return bilen, eller null om verkstaden är tom.
     */
    public T unload() {
        if (storedCars.isEmpty()) return null;
        return storedCars.removeLast();
    }
}

