package CarSimulator.Models.States;

import CarSimulator.Models.hasFlatbed;

public class LiftBedState implements State{
    @Override
    public void handlerequest() {
        ((hasFlatbed) motorVehicle).RaiseFlatbed(45);
    }
}
