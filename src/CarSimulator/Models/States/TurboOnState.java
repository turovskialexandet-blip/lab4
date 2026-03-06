package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.hasTurbo;

public class TurboOnState implements State{
    @Override
    public void handlerequest(Motor_vehicle vehicle) {
        ((hasTurbo) vehicle).setTurboOn();
    }
}
