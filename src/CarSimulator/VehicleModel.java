package CarSimulator.Models;

import java.util.ArrayList;
import java.util.List;

public class VehicleModel {
    private final ArrayList<Motor_vehicle> cars = new ArrayList<>();
    private final List<VehicleObserver> observers = new ArrayList<>();

    public ArrayList<Motor_vehicle> getVehicles() {
        return cars;
    }

    public void addVehicle(Motor_vehicle vehicle) {
        cars.add(vehicle);
        notifyVehicleAdded();
    }

    public void removeLastVehicle() {
        if (!cars.isEmpty()) {
            cars.remove(cars.size() - 1);
            notifyVehicleRemoved();
        }
    }

    public void addObserver(VehicleObserver observer) {
        observers.add(observer);
    }

    public void notifyVehicleAdded() {
        for (VehicleObserver observer : observers) {
            observer.vehicleAdded();
        }
    }

    public void notifyVehicleRemoved() {
        for (VehicleObserver observer : observers) {
            observer.vehicleRemoved();
        }
    }

    public void notifyVehicleMoved() {
        for (VehicleObserver observer : observers) {
            observer.vehicleMoved();
        }
    }

    public void notifyVehicleStateChanged() {
        for (VehicleObserver observer : observers) {
            observer.vehicleStateChanged();
        }
    }
}
