package frc.spline;

import frc.utils.geometry.Pose2d;
import frc.utils.geometry.Translation2d;
import frc.utils.geometry.Rotation2d;

public class QuinticHermiteSpline {

    private double x0, x1, dx0, dx1, ddx0, ddx1, y0, y1, dy0, dy1, ddy0, ddy1;
    private double ax, bx, cx, dx, ex, fx, ay, by, cy, dy, ey, fy;

    //p0 is start point, p1 is end point
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


    
}