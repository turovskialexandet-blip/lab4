package CarSimulator.Models.States;

public class StopState implements State{
    @Override
    public void handlerequest() {
        motorVehicle.stopEngine();
    }
}
