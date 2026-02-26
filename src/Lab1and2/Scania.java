package Lab1and2;

import java.awt.*;

public class Scania extends Truck<Object>{
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
            // då du har redan lagt till detta vilkoret i Lab1and2.Scania och Lab1and2.MAN så tog jag brot det
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
    public void startEngine(){ if (getFlatBedAngle() == 0) currentSpeed = 0.1; }

    @Override
    public double speedFactor(){
        double turbo = 1;
        if(turboCharger.getTurbo()) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }

    public void setTurboOn(){ turboCharger.setTurboOn(); }

    public void setTurboOff(){ turboCharger.setTurboOff(); }
}
