package Lab1and2;

import java.awt.*;

public class Car extends Motor_vehicle {
    private final int nrDoors; // Number of doors on the car
    private Color color; // Color of the car
    //private final String modelName; // The car model

    public Car(int nrDoors, double enginePower, Color color, String modelName) {
        this.nrDoors = nrDoors;
        setEnginePower(enginePower);
        this.color = color;
        setModelName(modelName);
        stopEngine();
    }

    // getter-function that returns the number of doors a car has
    public int getNrDoors(){ return nrDoors; }

    //public String getModelName(){return modelName; }

    // getter-function that returns the color of the car
    public Color getColor(){ return color; }

    // setter-function to set the color of the car
    public void setColor(Color clr){ color = clr; }
}
