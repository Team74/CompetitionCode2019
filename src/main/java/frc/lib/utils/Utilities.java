package frc.lib.utils;

public class Utilities {
    
    //Really small number
    public static final double kEpsilon = 1e-12;

    //Prevent this class from being instantiated
    private Utilities(){

    }

    public static double handleDeadband(double input, double kDeadband) {
        return Math.abs(input) <= kDeadband ? 0 : input; 
    }

    public static boolean doubleToBool(double input){
        boolean temp = Math.abs(input) > 0.0 ? true : false;
        return temp;
    }

    public static double encoderToAngle(double encoder, double countsPerRev){
        double angle = encoder/countsPerRev * 360;
        return angle;
    }

    public static double angleToEncoder(double angle, double countsPerRev){
        double encoder = (angle/360) * countsPerRev;
        return encoder;
    }

    public static double[] angleToUnitVector(double _angle){
        double[] vector = new double[] { 
            Math.cos(_angle),
            Math.sin(_angle)
        };
        return vector;
    }
    
    //Limits the given input to the given magnitude
    public static double limit(double v, double maxMagnitude) {
        return limit(v, -maxMagnitude, maxMagnitude);
    }

    public static double limit(double v, double min, double max) {
        return Math.min(max, Math.max(min, v));
    }

    public static double interpolate(double a, double b, double x) {
        x = limit(x, 0.0, 1.0);
        return a + (b-a) * x;
    }

    public static boolean epsilonEquals(double a, double b, double epsilon) {
        return ((a - epsilon <= b) && (a + epsilon >= b));
    }

    public static boolean epsilonEquals(double a, double b) {
        return epsilonEquals(a, b, kEpsilon);
    }

    public static boolean epsilonEquals(int a, int b, int epsilon) {
        return (a - epsilon <= b) && (a + epsilon >= b);
    }
}