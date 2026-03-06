package CarSimulator;

import CarSimulator.Models.*;
import CarSimulator.Models.Vehicle_models.Volvo240;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private final Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Motor_vehicle> cars = new ArrayList<>();

    private static CollisionHandler collisionHandler;

    //workShop object for Volvo240
    private final Workshop<Volvo240> volvoBrand = new Workshop<>(2);

    private final Point volvoWorkshopPoint = new Point(300, 0);
    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        cc.cars.add(Motor_vehicleFactory.createVolvo240());
        cc.cars.add(Motor_vehicleFactory.createSaab95());
        cc.cars.add(Motor_vehicleFactory.createScania());

        //cars starting 100 pixels away from each other
        cc.carStartPositions();

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);


        // Registrerar DrawPanel som observer på bilar efter de skapats
        for (Motor_vehicle car : cc.cars) {
            car.addObserver(cc.frame);
        }

        collisionHandler = new CollisionHandler(cc.frame);
        // Start the timer
        cc.timer.start();
    }

    //spawning 100 pixels away from each other
    public void carStartPositions() {
        for (int i = 0; i < cars.size(); i++){
            Motor_vehicle car = cars.get(i);
            car.getCoordinates().x = 0; //all start at 0
            car.getCoordinates().y = i * 100; //index * 100 --> 100 pixels away in y
        }
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
     * view to update its images. Change this method to your needs.
     * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = cars.size() - 1; i >= 0; i--) {
                Motor_vehicle car = cars.get(i);
                car.move(); //notifierar DrawPanel via observer

                int x = car.getCoordinates().x;
                int y = car.getCoordinates().y;

                collisionHandler.hitWallCollision(x, y, car);
                if (collisionHandler.hitWorkshopCollision(x, y, i, car, volvoWorkshopPoint)){
                    volvoBrand.load((Volvo240) car);
                    cars.remove(car);
                }
            }

            // repaint en gång efter att alla bilar uppdaterats
            //frame.repaintDrawPanel(); //behövs inte längre efter observer
        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Motor_vehicle car : cars) {
            car.gas(gas);
        }
    }

    void brake(int amount) {
        double brakeAmount = ((double) amount) / 100;
        for (Motor_vehicle car : cars) {
            car.brake(brakeAmount);
        }

    }

    void start() {
        for (Motor_vehicle car : cars) {
            car.startEngine();
        }
    }

    void stop() {
        for (Motor_vehicle car : cars) {
            car.stopEngine();
        }
    }

    void turboOn() {
        for (Motor_vehicle car : cars) {
            if (car instanceof hasTurbo) ((hasTurbo) car).setTurboOn();
        }
    }

    void turboOff() {
        for (Motor_vehicle car : cars) {
            if (car instanceof hasTurbo) ((hasTurbo) car).setTurboOff();
        }
    }

    void liftBed () {
        for (Motor_vehicle car : cars) {
            if (car instanceof hasFlatbed) ((hasFlatbed) car).LowerFlatbed(45);
        }
    }

    void lowerBed () {
        for (Motor_vehicle car : cars) {
            if (car instanceof hasFlatbed) ((hasFlatbed) car).RaiseFlatbed(45);
        }
    }

    void addCar () {
    }

    void removeCar () {
        cars.removeLast();
    }



    // Getter for vehicles (used by Lab1and2.CarView / DrawPanel)
    public ArrayList<Motor_vehicle> getVehicles() {
        return cars;
    }
}