package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

public class StateTracker implements Updateable {
    public SubsystemManager m_subsystem_manager;
    private double swerveVectors[][];
    public DriverStation driverStation;
    public double xPose;
    public double yPose;
    public double heading;

    public StateTracker(SubsystemManager _subsystem_manager){
        m_subsystem_manager = _subsystem_manager;
    }

    //We can get a vector for each pod and use them to figure out how the robot is moving throughout auton and the match.
    public void update(double dt){
        double xPoseMod = 0;
        double yPoseMod = 0;    
        swerveVectors = m_subsystem_manager.m_drivetrain.m_swerveVectors;
        xPose += xPoseMod;
        yPose += yPoseMod;
        heading = m_subsystem_manager.m_drivetrain.gyro.getAngle();
    }

}