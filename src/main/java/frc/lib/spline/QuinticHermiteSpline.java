package frc.lib.spline;

import frc.lib.utils.geometry.Pose2d;
import frc.lib.utils.geometry.Rotation2d;
import frc.lib.utils.geometry.Translation2d;

public class QuinticHermiteSpline extends Spline {

    private double x0, x1, dx0, dx1, ddx0, ddx1, y0, y1, dy0, dy1, ddy0, ddy1;
    private double ax, bx, cx, dx, ex, fx, ay, by, cy, dy, ey, fy;

    /*
    p0 is start point, p1 is end point
    fairly sure scale undoes the normalization, but not sure about the * 1.2
    ddx and ddy are set to 0 to simplify the math, maybe I'll figure out a way to optimize them
    */ 
    public QuinticHermiteSpline(Pose2d p0, Pose2d p1){
        double scale = 1.2 * p0.getTranslation().distance(p1.getTranslation());
        x0 = p0.getTranslation().x();
        x1 = p1.getTranslation().x();
        dx0 = p0.getRotation().cos() * scale;
        dx1 = p1.getRotation().cos() * scale;
        ddx0 = 0;
        ddx1 = 0;
        y0 = p0.getTranslation().y();
        y1 = p1.getTranslation().y();
        dy0 = p0.getRotation().sin() * scale;
        dy1 = p1.getRotation().sin() * scale;
        ddy0 = 0;
        ddy1 = 0;

        computeCoefficients();
    }

    /*
        Re-arranges the spline into an at^5 + bt^4 + ... + f form for simpler computations
        Further reading on these equations can be found here:
            https://www.google.com/url?q=https://www.rose-hulman.edu/~finn/CCLI/Notes/day09.pdf&source=gmail&ust=1550460099570000&usg=AFQjCNEvPaMOIsWEvbKLQz4gs86-p4QY9Q
    */
    private void computeCoefficients(){
        ax = -6 * x0 - 3 * dx0 - 0.5 * ddx0 + 0.5 * ddx1 - 3 * dx1 + 6 * x1;
        bx = 15 * x0 + 8 * dx0 + 1.5 * ddx0 - ddx1 + 7 * dx1 - 15 * x1;
        cx = -10 * x0 - 6 * dx0 - 1.5 * ddx0 + 0.5 * ddx1 - 4 * dx1 + 10 * x1;
        dx = 0.5 * ddx0;
        ex = dx0;
        fx = x0;

        ay = -6 * y0 - 3 * dy0 - 0.5 * ddy0 + 0.5 * ddy1 - 3 * dy1 + 6 * y1;
        by = 15 * y0 + 8 * dy0 + 1.5 * ddy0 - ddy1 + 7 * dy1 - 15 * y1;
        cy = -10 * y0 - 6 * dy0 - 1.5 * ddy0 + 0.5 * ddy1 - 4 * dy1 + 10 * y1;
        dy = 0.5 * ddy0;
        ey = dy0;
        fy = y0;
    }

    public Pose2d getStartPose() {
        return new Pose2d(
            new Translation2d(x0, y0),
            new Rotation2d(dx0, dy0, true)
        );
    }

    public Pose2d getEndPose() {
        return new Pose2d(
            new Translation2d(x1, y1),
            new Rotation2d(dx1, dy1, true)
        );
    }

    //returns a point on this spline at time t where t is between 0 and 1
    public Translation2d getPoint(double t) {
        double temp_x = ax * Math.pow(t, 5) + bx * Math.pow(t, 4) + cx * Math.pow(t, 3) + dx * Math.pow(t, 2) + ex * t + fx;
        double temp_y = ay * Math.pow(t, 5) + by * Math.pow(t, 4) + cy * Math.pow(t, 3) + dy * Math.pow(t, 2) + ey * t + fy;
        return new Translation2d(temp_x, temp_y);
    }

    //We can get the derivitive at a certain point by deriving the above equations for x and y
    private double dx(double t) {
        return 5 * ax * Math.pow(t, 4) + 4 * bx * Math.pow(t, 3) + 3 * cx * Math.pow(t, 2) + 2 * dx * t + ex;
    }

    private double dy(double t) {
        return 5 * ay *Math.pow(t, 4) + 4 * by * Math.pow(t, 3) + 3 * cy * Math.pow(t, 2) + 2 * dy * t + ey;
    }

    private double ddx(double t) {
        return 20 * ax * Math.pow(t, 3) + 12 * bx * Math.pow(t, 2) + 6 * cx * t + 2 * dx;
    }

    private double ddy(double t) {
        return 20 * ay * Math.pow(t, 3) + 12 * by * Math.pow(t, 2) + 6 * cy * t + 2 * dy;
    }

    private double dddx(double t) {
        return 60 * ax * Math.pow(t, 2) + 24 * bx * t + 6 * cx;
    }

    private double dddy(double t) {
        return 60 * ay * Math.pow(t, 2) + 24 * by * t + 6 * cy;
    }

    public double getVelocity(double t) {
        return Math.hypot(dx(t), dy(t));
    }

    public double getCurvature(double t) {
        return (dx(t) * ddy(t) - ddx(t) * dy(t)) / ((dx(t) * dx(t) + dy(t) * dy(t)) * Math.sqrt((dx(t) * dx(t) + dy(t) * dy(t))));
    }

    public  double getDCurvature(double t) {
        double dx2dy2 = (dx(t) * dx(t) + dy(t) * dy(t));
        double num = (dx(t)*dddy(t) - dddx(t)*dy(t)) * dx2dy2 - 3 * (dx(t) * ddy(t) - ddx(t) * dy(t)) * (dx(t) * ddx(t) + dy(t) * ddy(t));
        return num / (dx2dy2 * dx2dy2 * Math.sqrt(dx2dy2));
    }

    public double dCurvature2(double t) {
        double dx2dy2 = (dx(t) * dx(t) + dy(t) * dy(t));
        double num = (dx(t)*dddy(t) - dddx(t)*dy(t)) * dx2dy2 - 3 * (dx(t) * ddy(t) - ddx(t) * dy(t)) * (dx(t) * ddx(t) + dy(t) * ddy(t));
        return num * num / (dx2dy2 * dx2dy2 * dx2dy2 * dx2dy2 * dx2dy2);
    }

    //Method to get the heading of the spline at point t
    public Rotation2d getHeading(double t) {
        return new Rotation2d(dx(t), dy(t), true);
    }   
}