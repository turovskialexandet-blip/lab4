package Lab1and2;

import java.awt.*;

public class Vehicle implements Movable {
    private final Point coordinates = new Point(0, 0);
    private int direction_state = 1;
    public double currentSpeed;

    public void move(){
        switch (direction_state) {
            case 0: //rakt fram
                coordinates.y += (int) currentSpeed;
                break;
            case 1: //går åt höger
                coordinates.x += (int) currentSpeed;
                break;
            case 2: //går ner
                coordinates.y -= (int) currentSpeed;
                break;
            case 3: //går åt vänster
                coordinates.x -= (int) currentSpeed;
                break;
        }
    }

    public void turnLeft(){ direction_state = (direction_state + 3 ) % 4; }

    public void turnRight(){ direction_state = (direction_state + 1) % 4; }

    public Point getCoordinates(){ return coordinates; }

    public double getCurrentSpeed(){ return currentSpeed; }

    public double speedFactor(){ return 0; }

    public void incrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() + speedFactor() * amount,0);
    }

    public void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }

    public int getDirection_state(){return direction_state;}

    public double Zero_to_One(double amount){
        if (amount <= 0) amount = 0;
        else amount = Math.min(amount, 1);
        //System.out.printf("Call from Zero_to_One() function, the amount is %s\n", amount);
        return amount;
    }

}
