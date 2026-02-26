package Lab1and2;

public class Motor_vehicleFactory {
    public Volvo240 createVolvo240(){
        return new Volvo240();
    }
    public Saab95 createSaab95(){
        return new Saab95();
    }
    public Scania createScania(){
        return new Scania();
    }
}
