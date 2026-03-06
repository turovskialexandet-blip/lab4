package CarSimulator.Models;

import CarSimulator.Models.States.State;

import java.util.ArrayList;
import java.util.List;

public abstract class Motor_vehicle extends Vehicle {
    private double enginePower;
    private List<VehicleObserver> observers = new ArrayList<>();
    private boolean engineOn;
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

    public abstract String getModelName();

    public void startEngine(){
        setCurrentSpeed(0.1);
        engineOn = true;
        System.out.println(engineOn);
        System.out.println(getCurrentSpeed());
    }
    public void stopEngine(){
        setCurrentSpeed(0);
        engineOn = false;
        System.out.println(engineOn);
        System.out.println(getCurrentSpeed());
    }

    public void setEnginePower(double amount){ enginePower = amount; }

    public void setState(State state){
        this.state = state;
        System.out.println(state);
    }

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
        //System.out.println("Reached");
        amount = Zero_to_One(amount);
        if (engineOn) incrementSpeed(amount);
        /*
        if (engineOn){
            amount = Zero_to_One(amount);
            incrementSpeed(amount);
        }*/
    }

    public void brake(double amount){
        //System.out.println("Reached");
        amount = Zero_to_One(amount);
        if (engineOn) decrementSpeed(amount);
        /*if (engineOn){
            amount = Zero_to_One(amount);
            decrementSpeed(amount);
        }*/
    }
}
