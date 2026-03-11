package CarSimulator.Models;

public interface VehicleObserver {
    void vehicleMoved();
    void vehicleRemoved();
    void vehicleAdded();
}
