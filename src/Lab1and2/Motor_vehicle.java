package Lab1and2;

public class Motor_vehicle extends Vehicle {
    private double enginePower;
    private String modelName; // The vehicle model

    public double getEnginePower(){ return enginePower; }

    //public boolean getTurbo(){ return turboOn; }

    public String getModelName(){return modelName; }

    public void startEngine(){ currentSpeed = 0.1; }

    public void stopEngine(){ currentSpeed = 0; }

    public void setEnginePower(double amount){ enginePower = amount; }

    public void setModelName(String name){ modelName = name; }

    @Override
    public void incrementSpeed(double amount){ currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower()); }

    public void gas(double amount){
        //System.out.println("Pressed");
        amount = Zero_to_One(amount);
        incrementSpeed(amount);
    }

    public void brake(double amount){
        //System.out.println("Pressed");
        amount = Zero_to_One(amount);
        decrementSpeed(amount);
    }
}
