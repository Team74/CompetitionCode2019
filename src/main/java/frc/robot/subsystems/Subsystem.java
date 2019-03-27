package frc.robot.subsystems;

import com.sun.javadoc.Type;

/*
Template for creating the subsystems on the robot
*/

public interface Subsystem {
    /*
    Sets the state of the subsystem automatically
    What it sets the state to depends on the robot
    */
    public void updateState();

    /*
    The talons need an int for velocity and position control, a double for percent output
    the sparkmax takes a double for all inputs
    Hence the need for number
    */
    public void setTarget(Number _target);

    public Number getTarget();

    public void setManual(boolean _temp);

    public boolean isManual();

    public void update(double dt);
}