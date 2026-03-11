package CarSimulator.Models.Vehicle_models;

import CarSimulator.Models.Car;
import CarSimulator.Models.TurboCharger;
import CarSimulator.Models.hasTurbo;

import java.awt.*;

public class Saab95 extends Car implements hasTurbo {

    private final TurboCharger turboCharger = new TurboCharger();
    
    public Saab95(){
        super(2, 125, Color.red, "Saab95", "/pics/Saab95.jpg");
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
