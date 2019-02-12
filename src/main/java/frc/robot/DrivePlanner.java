package frc.robot;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import frc.robot.subsystems.Drivetrain;

public class DrivePlanner implements Updateable {
    private SubsystemManager m_subsystemManager;
    private Drivetrain m_drivetrain;

    public double speed = 0.0;
    public double angle = 0.0;
    public double rotation = 0.0;

    public DrivePlanner(SubsystemManager subsystemManager){
        m_subsystemManager = subsystemManager;
        m_drivetrain = m_subsystemManager.m_drivetrain;
    }

    public void update(double dt){
        m_drivetrain.setMove(speed, angle, rotation);
    }
}