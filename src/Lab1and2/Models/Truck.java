package Lab1and2.Models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Truck <Cargo> extends Motor_vehicle {
    private final int nrDoors; // Number of doors on the car
    private Color color; // Color of the vehicle
    private final List<Cargo> truckCargo;
    private final String modelName; // The car model


    public Truck(int nrDoors, double enginePower, Color color, String modelName, String imagePath) {
        this.nrDoors = nrDoors;
        setEnginePower(enginePower);
        this.color = color;

        this.modelName = modelName;
        setImagePath(imagePath);

        this.truckCargo = new ArrayList<>();
        stopEngine();
    }

    // getter-function that returns the number of doors a car has
    public int getNrDoors() {
        return nrDoors;
    }

    public List<Cargo> getTruckCargo() {
        return truckCargo;
    }

    // getter-function that returns the color of the car
    public Color getColor() {
        return color;
    }

    // setter-function to set the color of the car
    public void setColor(Color clr) {
        color = clr;
    }

    public void Load(Cargo cargo) {
        truckCargo.add(cargo);
    }

    public void offLoad() {
        if (!truckCargo.isEmpty()) {
            truckCargo.remove(truckCargo.size() - 1);
        }
    }

    @Override
    public String getModelName() {
        return modelName;
    }
}
