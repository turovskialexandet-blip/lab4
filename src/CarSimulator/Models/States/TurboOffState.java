package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.hasFlatbed;
import CarSimulator.Models.hasTurbo;

public class TurboOffState implements State{
    @Override
    public void handlerequest(Motor_vehicle vehicle) {
        ((hasTurbo) vehicle).setTurboOff();
    }
}
