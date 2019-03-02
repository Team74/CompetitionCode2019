package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

public class StateTracker implements Updateable {
    public SubsystemManager mSubsytemManager;
    private double swerveVectors[][];
    public DriverStation driverStation;
    public double xPose;
    public double yPose;
    public double heading;

    public StateTracker(SubsystemManager _subsystem_manager){
        mSubsytemManager = _subsystem_manager;
    }

    //We can get a vector for each pod and use them to figure out how the robot is moving throughout auton and the match.
    //aka Inverse Kinematics
    public void update(double dt){
    }

}