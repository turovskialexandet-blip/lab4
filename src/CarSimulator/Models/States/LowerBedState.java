package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.hasFlatbed;

public class LowerBedState implements State{
    @Override
    public void handlerequest(Motor_vehicle vehicle) {
        ((hasFlatbed) vehicle).LowerFlatbed(45);
    }
}
