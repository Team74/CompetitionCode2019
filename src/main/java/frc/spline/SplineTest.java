package frc.spline;

import frc.spline.Spline;
import frc.spline.Points;
/*
Temp class for testing spline tools
*/
public class SplineTest {

    public Points points = new Points();

    public Spline test_CenterStartToLeftRocketCloseHatch;

    public SplineTest(){
        test_CenterStartToLeftRocketCloseHatch = new Spline(
            points.middleStart, points.leftRocketCloseHatch, 0, 0
            );
    }
}
