package Lab1and2;

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
