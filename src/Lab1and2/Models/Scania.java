package Lab1and2.Models;

import Lab1and2.Flatbed;
import Lab1and2.Truck;
import Lab1and2.TurboCharger;
import Lab1and2.hasFlatbed;

import java.awt.*;

public class Scania extends Truck<Object> implements hasFlatbed {
    private final Flatbed flatbed;
    private final TurboCharger turboCharger;

    public Scania(){
        super(2, 550, Color.BLUE, "Scania");
        flatbed = new Flatbed();
        turboCharger = new TurboCharger();
    }

    public double getFlatBedAngle(){ return flatbed.getFlatBedAngle(); }

    public void RaiseFlatbed(double angle) {
        if (getCurrentSpeed() == 0) {
            // då du har redan lagt till detta vilkoret i Lab1and2.Models.Scania och Lab1and2.Models.MAN så tog jag brot det
            // från Lab1and2.Flatbed.java
            flatbed.RaiseFlatbed(angle);
        }
    }

    public void LowerFlatbed(double angle){ flatbed.LowerFlatbed(angle); }

    @Override
    public void gas(double amount) {
        if (getFlatBedAngle() == 0) {
            super.gas(amount);
        }
    }

    @Override
    public void startEngine(){ if (getFlatBedAngle() == 0) setCurrentSpeed(0.1); }

    @Override
    public double speedFactor(){
        double turbo = 1;
        if(turboCharger.getTurbo()) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }

    public void setTurboOn(){ turboCharger.setTurboOn(); }

    public void setTurboOff(){ turboCharger.setTurboOff(); }
}
