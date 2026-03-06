package CarSimulator.Models.States;

import CarSimulator.Models.hasFlatbed;
import CarSimulator.Models.hasTurbo;

public class TurboOffState implements State{
    @Override
    public void handlerequest() {
        ((hasTurbo) motorVehicle).setTurboOff();
    }
}
