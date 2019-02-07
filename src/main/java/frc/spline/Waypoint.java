package frc.spline;

public class Waypoint {

    public double[] heading;
    public double x;
    public double y;

    public Waypoint(double _xPose, double _yPose, double _heading){
        x = _xPose;
        y = _yPose;
        double[] temp = angleToUnitVector(_heading);
        heading[0] = temp[0];
        heading[1] = temp[1];
    }

    public double[] angleToUnitVector(double _angle){
        double[] vector = new double[] { 
            Math.cos(_angle),
            Math.sin(_angle)
        };
        return vector;
    }

}