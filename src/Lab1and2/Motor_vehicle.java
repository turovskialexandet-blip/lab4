package Lab1and2;

public class Motor_vehicle extends Vehicle {
    private double enginePower;
    private String modelName; // The vehicle model

    private String imagePath;
    public String getImagePath() { return imagePath; }
    protected void setImagePath(String path) { this.imagePath = path; }

    public double getEnginePower(){ return enginePower; }

    //public boolean getTurbo(){ return turboOn; }

    public String getModelName(){return modelName; }

    public void startEngine(){ setCurrentSpeed(0.1); }

    public void stopEngine(){ setCurrentSpeed(0); }

    public void setEnginePower(double amount){ enginePower = amount; }

    public void setModelName(String name){ modelName = name; }

    @Override
    public void incrementSpeed(double amount){
        setCurrentSpeed(Math.min(getCurrentSpeed() + speedFactor() * amount, getEnginePower()));
    }

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
