package Lab1and2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Truck <Cargo> extends Motor_vehicle{
    private final int nrDoors; // Number of doors on the car
    private Color color; // Color of the vehicle
    private final String modelName; // The name model
    private final List<Cargo> truckCargo;

    public Truck(int nrDoors, double enginePower, Color color, String modelName){
        this.nrDoors = nrDoors;
        setEnginePower(enginePower);
        this.color = color;
        this.modelName = modelName;
        this.truckCargo = new ArrayList<>();
        stopEngine();
    }

    // getter-function that returns the number of doors a car has
    public int getNrDoors(){ return nrDoors; }

    public List<Cargo> getTruckCargo(){ return truckCargo; }

    // getter-function that returns the color of the car
    public Color getColor(){ return color; }

    // setter-function to set the color of the car
    public void setColor(Color clr){ color = clr; }

    public void Load(Cargo cargo){ truckCargo.add(cargo); }

    public void Off_Load(){ truckCargo.removeLast(); }
}
