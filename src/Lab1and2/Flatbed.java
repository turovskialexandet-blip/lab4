package Lab1and2;

public class Flatbed {
    private double FlatBedAngle = 0;
    private boolean RampLowered = false;

    public double getFlatBedAngle(){ return FlatBedAngle; }

    public boolean getRampLowered(){ return RampLowered; }

    public void RaiseFlatbed(double angle){
            FlatBedAngle = Math.min(FlatBedAngle + angle, 70);
    }

    public void LowerFlatbed(double angle){
            FlatBedAngle = Math.min(FlatBedAngle - angle, 70);
    }

    public void RaiseRamp(){ RampLowered = false; }
    public void LoweredRamp(){ RampLowered = true; }
}
