package CarSimulator.Models.States;

public class StartState implements State{
    @Override
    public void handlerequest() {
        motorVehicle.startEngine();
    }
}
