package CarSimulator.Models.States;

import CarSimulator.Models.Motor_vehicle;

public interface State {
    Motor_vehicle motorVehicle = new Motor_vehicle() {
        @Override
        public String getModelName() {
            return "";
        }
    };
    void handlerequest(Motor_vehicle vehicle);
}
