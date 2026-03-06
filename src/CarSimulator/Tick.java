package CarSimulator;

import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.Vehicle_models.Volvo240;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tick {
    private final int delay = 50;
    private final Timer timer = new Timer(delay, new TimerListener());
    private ArrayList<Motor_vehicle> cars;
    private final CollisionHandler collisionHandler;
    private final CarController cc; //= new CarController();


    public Tick(ArrayList<Motor_vehicle> cars, CollisionHandler collisionHandler, CarController cc){
        this.cars = cars;
        this.collisionHandler = collisionHandler; //
        this.cc = cc; //
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

                //kolla om kört in i vägg
                collisionHandler.hitWallCollision(x, y, car);

                //om fordonet kör in i verkstad
                if (collisionHandler.hitWorkshopCollision(x, y, i, car, cc.getVolvoWorkshopPoint())){
                    cc.loadVolvoBrand(car);
                    cars.remove(car);
                }
            }
            //frame.repaintDrawPanel(); //behövs inte längre efter observer
        }
    }
}
