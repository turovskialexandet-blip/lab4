package CarSimulator.Models.States;

import CarSimulator.Models.hasTurbo;

public class TurboOnState implements State{
    @Override
    public void handlerequest() {
        ((hasTurbo) motorVehicle).setTurboOn();
    }
}
