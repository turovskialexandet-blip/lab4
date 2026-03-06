package CarSimulator;

import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.Vehicle_models.Volvo240;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tick {
    private final int delay = 50;
    private final Timer timer = new Timer(delay, new Tick.TimerListener());
    private ArrayList<Motor_vehicle> cars;
    private static CollisionHandler collisionHandler;
    private static CarController cc = new CarController();


    public Tick(ArrayList<Motor_vehicle> cars){
        this.cars = cars;
        collisionHandler = cc.getCollisionHandler();
    }

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
}
