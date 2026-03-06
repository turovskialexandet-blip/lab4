package CarSimulator.Models;

import CarSimulator.Models.States.State;

import java.util.ArrayList;
import java.util.List;

public abstract class Motor_vehicle extends Vehicle {
    private double enginePower;
    private List<VehicleObserver> observers = new ArrayList<>();
    private State state;

    // registrera på listan
    public void addObserver(VehicleObserver o) {
        observers.add(o);
    }

    // anropar positionChanged på alla registrerade
    private void notifyObservers() {
        for (VehicleObserver o : observers) {
            o.positionChanged();
        }
    }

    private String imagePath;
    public String getImagePath() { return imagePath; }
    protected void setImagePath(String path) { this.imagePath = path; }

    public double getEnginePower(){ return enginePower; }

    //public boolean getTurbo(){ return turboOn; }

    public abstract String getModelName();

    public void startEngine(){ setCurrentSpeed(0.1); }
    public void stopEngine(){ setCurrentSpeed(0); }

    public void setEnginePower(double amount){ enginePower = amount; }

    public void setState(State state){ this.state = state; }

    public void request(){ state.handlerequest(this); }

    //
    @Override
    public void move() {
        super.move();
        notifyObservers();
    }

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
