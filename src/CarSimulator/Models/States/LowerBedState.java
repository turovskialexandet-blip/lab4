package CarSimulator.Models.States;

import CarSimulator.Models.hasFlatbed;

public class LowerBedState implements State{
    @Override
    public void handlerequest() {
        ((hasFlatbed) motorVehicle).LowerFlatbed(45);
    }
}
