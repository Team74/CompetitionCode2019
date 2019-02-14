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
}