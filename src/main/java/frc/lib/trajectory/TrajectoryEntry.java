package frc.lib.trajectory;

//Class to hold all entries in a trajectory
public class TrajectoryEntry<S> {
    protected final S pose;//Defined as S because theoretically this could be a Pose2d, or a translation2d, or a pose2d with curvature, but it represents a position os it's pose.
    protected double t;//Seconds
    protected double velocity;//Inches per second
    protected double acceleration;//Inches per second^2

    public TrajectoryEntry(final S _pose) {
        pose = _pose;
    }

    public void set_t(double _t) {
        t = _t;
    }

    public void setVelocity(double _velocity) {
        velocity = _velocity;
    }

    public void setAcceleration(double _acceleration) {
        acceleration = _acceleration;
    }

    public double t() {
        return t;
    }

    public S state() {
        return pose;
    }

    public double velocity() {
        return velocity;
    } 

    public double acceleration() {
        return acceleration;
    }
}