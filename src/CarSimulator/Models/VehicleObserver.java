package CarSimulator.Models;

public interface VehicleObserver {
    void positionChanged();
    void vehicleRemoved();
    void vehicleAdded();
}
