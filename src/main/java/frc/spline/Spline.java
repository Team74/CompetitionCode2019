package frc.spline;

import frc.spline.SplineGenerator;

public class Spline {

    public SplineGenerator mSplineGenerator = new SplineGenerator();
    public double[] spline;

    public Spline(
        Waypoint pointOne,
        Waypoint pointTwo,
        double startVelocity, 
        double endVelocity
        ){
        spline = mSplineGenerator.generateSpline(pointOne, pointTwo, startVelocity, endVelocity);
    }
    
}