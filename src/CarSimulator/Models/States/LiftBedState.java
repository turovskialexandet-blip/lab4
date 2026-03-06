package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.hasFlatbed;

public class LiftBedState implements State{
    @Override
    public void handlerequest(Motor_vehicle vehicle) {
        ((hasFlatbed) vehicle).RaiseFlatbed(45);
    }
}
