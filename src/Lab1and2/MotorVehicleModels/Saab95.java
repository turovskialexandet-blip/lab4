package Lab1and2.MotorVehicleModels;

import Lab1and2.Car;
import Lab1and2.TurboCharger;
import Lab1and2.hasTurbo;

import java.awt.*;

public class Saab95 extends Car implements hasTurbo {

    private final TurboCharger turboCharger = new TurboCharger();
    
    public Saab95(){
        super(2, 125, Color.red, "Saab95");
        setTurboOff();
    }

    public void setTurboOn(){ turboCharger.setTurboOn(); }

    public void setTurboOff(){ turboCharger.setTurboOff(); }

    @Override
    public double speedFactor(){
        double turbo = 1;
        if(turboCharger.getTurbo()) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }
}
