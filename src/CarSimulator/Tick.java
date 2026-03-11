package CarSimulator;

import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.Vehicle_models.Volvo240;
import CarSimulator.Models.Workshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tick {
    private final int delay = 50;
    private final Timer timer = new Timer(delay, new TimerListener());
    private ArrayList<Motor_vehicle> cars;
    private final CollisionHandler collisionHandler;
    private final Workshop<Volvo240> workshop;
    private final Point workshopPoint;
    private final int maxX;
    private final int maxY;

    public Tick(ArrayList<Motor_vehicle> cars, CollisionHandler collisionHandler, Workshop<Volvo240> workshop,
                Point workshopPoint, int maxX, int maxY){
        this.cars = cars;
        this.collisionHandler = collisionHandler; //
        this.workshop = workshop;
        this.workshopPoint = workshopPoint;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    //starta tick - anropas från controller
    void start() {timer.start();}

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = cars.size() - 1; i >= 0; i--) {
                Motor_vehicle car = cars.get(i);
                car.move(); //notifierar DrawPanel via observer

                int x = car.getCoordinates().x;
                int y = car.getCoordinates().y;

                collisionHandler.hitWallCollision(x, y, car, maxX, maxY);

                if (collisionHandler.hitWorkshopCollision(x, y, i, car, workshopPoint)){
                    workshop.load((Volvo240) car);
                    cars.remove(car);
                }
            }
            //frame.repaintDrawPanel(); //behövs inte längre efter observer
        }
    }
}

