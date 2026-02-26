package Lab1and2;

import java.awt.*;
import java.util.List;
/**
 * Lab1and2.MAN - Biltransport
 * Regler:
 * - Rampen har två lägen: uppe eller nere.
 * - Rampen får bara vara nere när bilen står still.
 * - Bilar kan bara lastas/lossas när rampen är nere.
 * - Lossning sker FILO (sist in, först ut).
 * - Lastade bilar ska alltid ha samma position som biltransporten.
 */

public class MAN extends Truck<Car> {

    private final Flatbed flatbed;      // använder ramp funktionen i Lab1and2.Flatbed
    private final int maxCars;
    private final double maxLoadDistance;  // "rimligt nära"

    public MAN(int maxCars) {
        super(2, 400, Color.GRAY, "MAN"); //rimliga startvärden
        this.flatbed = new Flatbed();
        this.maxCars = maxCars;
        this.maxLoadDistance = 10.0;
    }

    //Ramp (uppe/nere)
    public boolean isRampLowered() {
        return flatbed.getRampLowered();
    }

    //Sänker rampen om Lab1and2.MAN står stilla.
    public void lowerRamp() {
        if (getCurrentSpeed() == 0) {
            flatbed.LoweredRamp();
        }
    }

    //Höjer rampen om Man står stilla
    public void raiseRamp() {
        if (getCurrentSpeed()==0) {
            flatbed.RaiseRamp();
        }
    }

    //Körregler: får inte köra om rampen är nere
    @Override
    public void gas(double amount) {
        // Biltransporten ska inte kunna köra om rampen är nere
        if (!isRampLowered()) {
            super.gas(amount);
            syncLoadedCarsPositions();
        }
    }

    @Override
    public void move() {
        super.move();
        syncLoadedCarsPositions();
    }

    //Lastning/Lossning
    /**
     * Lastar en bil om alla villkor är uppfyllda.
     * @return true om bilen lastades, annars false
     */
    public boolean loadCar(Car car) {
        // Rampen måste vara nere och Lab1and2.MAN måste stå still
        if (!isRampLowered() || getCurrentSpeed() != 0) return false;

        // Max antal bilar
        if (getTruckCargo().size() >= maxCars) return false;

        // "För stor bil" --> eget antagande
        if (car.getEnginePower() > 300) return false;

        // Måste vara nära
        if (!isCloseEnough(car)) return false;

        // Lasta (FILO via listan)
        getTruckCargo().add(car);

        // Synka position direkt vid lastning
        syncCarPosition(car);
        return true;
    }

    /**
     * Lossar sista bilen (FILO) om rampen är nere och Lab1and2.MAN står still.
     * @return bilen som lossades, eller null om det inte gick
     */
    public Car unloadCar() {
        if (!isRampLowered() || getCurrentSpeed() != 0) return null;

        List<Car> cargo = getTruckCargo();
        if (cargo.isEmpty()) return null;

        // FILO: ta sista
        // truckCargo är en List, därför tas sista elementet bort via index (size() - 1)
        // istället för removeLast(), som endast finns för Deque/LinkedList.
        Car car = cargo.remove(cargo.size() - 1);

        // Placera "rimligt nära" efter lossning (t.ex. Bakom Lab1and2.MAN)
        // (Vi tar en enkel regel: x - 1)
        car.getCoordinates().x = this.getCoordinates().x - 1;
        car.getCoordinates().y = this.getCoordinates().y;

        return car;
    }

    //Hjälpmetoder
    private boolean isCloseEnough(Car car) {
        Point a = this.getCoordinates();
        Point b = car.getCoordinates();

        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        return dist <= maxLoadDistance;
    }

    private void syncCarPosition(Car car) {
        // getCoordinates() returnerar en Point som går att ändra
        car.getCoordinates().x = this.getCoordinates().x;
        car.getCoordinates().y = this.getCoordinates().y;
    }

    private void syncLoadedCarsPositions() {
        for (Car car : getTruckCargo()) {
            syncCarPosition(car);
        }
    }
}
