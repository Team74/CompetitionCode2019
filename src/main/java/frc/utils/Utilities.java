package frc.utils;

public class Utilities {

    private Utilities(){

    }

    public static double handleDeadband(double input, double kDeadband) {
        return Math.abs(input) <= kDeadband ? 0 : input; 
    }

    public static boolean doubleToBool(double input){
        boolean temp = Math.abs(input) > 0 ? true : false;
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
}