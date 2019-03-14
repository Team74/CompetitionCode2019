package frc.robot;

import java.util.HashMap;

import frc.robot.subsystems.BallManipulator;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Wrist;

public class StateMachine implements Updateable {

    private SubsystemManager mSubsystemManager;
    
    public String elevatorSetpoint = "Bottom";
    public String wristTarget = "Stow";
    public String ballManipulatorTarget = "Hold";
    public String panelManipulatorTarget = "In";
    public String climberTarget = "Stowed";

    public StateMachine(SubsystemManager subsystemManager) {

        mSubsystemManager = subsystemManager;

    }

    public void update(double dt) {
    } 
}
