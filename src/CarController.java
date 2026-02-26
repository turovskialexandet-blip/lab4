import Lab1and2.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Motor_vehicle> cars = new ArrayList<>();

    //workShop object for Volvo240
    private final Workshop<Volvo240> volvoBrand = new Workshop<>(2);

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        cc.cars.add(new Lab1and2.Volvo240());
        cc.cars.add(new Lab1and2.Saab95());
        cc.cars.add(new Lab1and2.Scania());

        //cars starting 100 pixels away from each other
        cc.carStartPositions();

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

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
            // This
            for (int i = cars.size()-1; i >= 0; i--) {
                Motor_vehicle car = cars.get(i);
                car.move();
                int x = (int) Math.round(car.getCoordinates().x);
                int y = (int) Math.round(car.getCoordinates().y);
                frame.drawPanel.moveit(x, y, i);
                collisionHandler(x, y, i, car);
                // repaint() calls the paintComponent method of the panel
                frame.drawPanel.repaint();
                //current_Car++;
            }
        }
    }

    //handles all collisions
    private void collisionHandler(int x, int y, int index, Motor_vehicle car){
        hitWallCollision(x, y, car);
        hitWorkshopCollision(x, y, index, car);
    }
    // handles collision with walls
    private void hitWallCollision(int x, int y, Motor_vehicle car){

        //frame constrains
        int maxX = frame.drawPanel.getWidth() - 100; //how far you can move in x
        int maxY = frame.drawPanel.getHeight() - 60;

        if ((x >= maxX && car.getDirection_state() == 1) || //right wall, drive right
                (x <= 0 && car.getDirection_state() == 3) || //left wall, drive left
                (y >= maxY && car.getDirection_state() == 0) || //bottom, drive down
                (y <= 0 && car.getDirection_state() == 2) //up, drive up
        ) {
            car.stopEngine();
            car.turnLeft();
            car.turnLeft();
            car.startEngine();
        }
    }

    // handles collision with workshop
    private void hitWorkshopCollision(int x, int y, int index, Motor_vehicle car){
        Point workShopPos = frame.drawPanel.volvoWorkshopPoint;
        if (car instanceof Volvo240){
            System.out.println(String.format("XPos: %s, YPos: %s", x, y));
        }

        if (((x >= workShopPos.x &&  x < workShopPos.x + 101) &&
                (y >= workShopPos.y && y < workShopPos.y + 96))
                && car instanceof Volvo240
        ) {
            System.out.println("Collided");
            volvoBrand.load((Volvo240) car);
            frame.drawPanel.carMovedToWorkshop(index);
            cars.remove(car);
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
            if (car instanceof Saab95) ((Saab95) car).setTurboOn();
            else if (car instanceof Scania) ((Scania) car).setTurboOn();
        }
    }

    void turboOff() {
        for (Motor_vehicle car : cars) {
            if (car instanceof Saab95) ((Saab95) car).setTurboOff();
            else if (car instanceof Scania) ((Scania) car).setTurboOff();
        }
    }

    void liftBed () {
        for (Motor_vehicle car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).RaiseFlatbed(45);
            }
        }
    }

    void lowerBed () {
        for (Motor_vehicle car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).LowerFlatbed(45);
            }
        }
    }
}