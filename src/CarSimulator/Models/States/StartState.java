package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;

public class StartState implements State{
    @Override
    public void handlerequest(Motor_vehicle vehicle) {
        vehicle.startEngine();
    }
}
