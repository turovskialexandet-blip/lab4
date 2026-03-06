package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;

public class StopState implements State{
    @Override
    public void handlerequest(Motor_vehicle vehicle) {
        motorVehicle.stopEngine();
    }
}
