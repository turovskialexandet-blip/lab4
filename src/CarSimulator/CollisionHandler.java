package CarSimulator;

import CarSimulator.Models.Motor_vehicle;

import java.awt.*;
import java.util.Objects;

public class CollisionHandler {

    private CarView frame;

    public CollisionHandler (CarView frame){
        this.frame = frame;
    }

    public void hitWallCollision(int x, int y, Motor_vehicle car){

        //frame constrains
        int maxX = frame.getDrawPanelWidth() - 100; //how far you can move in x
        int maxY = frame.getDrawPanelHeight() - 60;

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

    public boolean hitWorkshopCollision(int x, int y, int index, Motor_vehicle car, Point volvoWorkshopPoint){
        Point workShopPos = volvoWorkshopPoint;

        if (((x >= workShopPos.x &&  x < workShopPos.x + 101) &&
                (y >= workShopPos.y && y < workShopPos.y + 96))
                && Objects.equals(car.getModelName(), "Volvo240")
        ) {
            return true;
        }
        return false;
    }

}
