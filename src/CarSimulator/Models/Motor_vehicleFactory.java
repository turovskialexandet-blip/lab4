package CarSimulator.Models;

import CarSimulator.Models.Vehicle_models.Saab95;
import CarSimulator.Models.Vehicle_models.Scania;
import CarSimulator.Models.Vehicle_models.Volvo240;

public class Motor_vehicleFactory {
    public static Volvo240 createVolvo240(){
        return new Volvo240();
    }
    public static Saab95 createSaab95(){
        return new Saab95();
    }
    public static Scania createScania(){
        return new Scania();
    }
}
