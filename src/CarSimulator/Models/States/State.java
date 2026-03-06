package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;

public interface State {
    void handlerequest(Motor_vehicle vehicle);
}
