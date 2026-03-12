package CarSimulator.Models;

import java.util.ArrayList;
import java.util.List;

public class VehicleModel {
    private final ArrayList<Motor_vehicle> vehicles = new ArrayList<>();
    private final List<VehicleObserver> observers = new ArrayList<>();

    // Returnerar listan med fordon
    public ArrayList<Motor_vehicle> getVehicles() {
        return vehicles;
    }

    // Lägger till ett fordon i modellen och notifierar observers
    public void addVehicle(Motor_vehicle vehicle) {
        vehicles.add(vehicle);
        notifyVehicleAdded();
    }

    // Tar bort sista fordonet och returnerar det
    public Motor_vehicle removeLastVehicle() {
        if (vehicles.isEmpty()) {
            return null;
        }

        Motor_vehicle removedVehicle = vehicles.remove(vehicles.size() - 1);
        notifyVehicleRemoved();
        return removedVehicle;
    }

    // Registrerar observer
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