package frc.lib.trajectory.timing;

import frc.lib.utils.Utilities;

public class TimedState<S> {
    protected final S state;
    protected double t;//Seconds
    protected double velocity;//Inches per second
    protected double acceleration;//Inches per second^2

    public TimedState(final S _state) {
        state = _state;
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
        return state;
    }

    public double velocity() {
        return velocity;
    } 

    public double acceleration() {
        return acceleration;
    }

    public TimedState<S> interpolate(TimedState<S> other, double x) {
        final double newT = Utilities.interpolate(t(), other.t(), x);
        final double deltaT = newT - t();
        if (deltaT < 0.0) {
            return other.interpolate(this, 1.0- x);
        }
        boolean revearsing = velocity() < 0.0 || (Utilities.epsilonEquals(velocity(), 0.0) && acceleration() < 0.0);
        final double newV = velocity() + acceleration() * deltaT;
        final double newS = (revearsing ? -1.0 : 1.0) * (velocity() * deltaT + .5 * acceleration() * Math.pow(deltaT, 2));
        return new TimedState<S>(state().interpolate(other.state(), newS / state().distance(other.state())), newT, newV, acceleration());
    }

    public double distance(TimedState(S) other) {
        return state().distance(other.state());
    }
}