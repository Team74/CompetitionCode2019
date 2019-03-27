package frc.robot;

import java.util.HashMap;

import frc.robot.subsystems.BallManipulator;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Wrist;

import frc.robot.subsystems.Wrist.WristState;
import frc.robot.subsystems.Elevator.ElevatorState;
import frc.robot.subsystems.BallManipulator.BallManipulatorState;

public class StateMachine implements Updateable {

    public static enum Configuration {
        Start,
        Travel,
        IntakePanel,
        LowPanel,
        MidPanel,
        HighPanel,
        IntakeBall,
        LowBall,
        MidBall,
        HighBall,
        CargoBall,
        L2Climb,
        L3Climb;
    }
    private SubsystemManager mSubsystemManager;

    private Configuration currentConfiguration = Configuration.Start;
    private String ElevatorTarget = "";
    private String wristTarget = "";
    private String ballManipulatorTarget = "";
    private String panelManipulatorTarget = "";
    private String climberTarget = "";

    private String tempElevatorTarget = "";
    private String tempWristTarget = "";
    private String tempBallManipulatorTarget = "";
    private String tempPanelManipulatorTarget = "";
    private String tempClimberTarget = "";

    public StateMachine(SubsystemManager subsystemManager) {
        mSubsystemManager = subsystemManager;

        setConfiguration_Start();
    }

    public void setElevatorTarget(String _target) {
        ElevatorTarget = _target;
    }

    public String getElevatorTarget() {
        return ElevatorTarget;
    }

    public void setWristTarget(String _target) {
        wristTarget = _target;
    }

    public String getWristTarget() {
        return wristTarget;
    }

    public void setBallManipulatorTarget(String _target) {
        ballManipulatorTarget = _target;
    }

    public String getBallManipulatorTarget() {
        return ballManipulatorTarget;
    }

    public void setPanelManipulatorTarget(String _target) {
        panelManipulatorTarget = _target;
    }

    public String getPanelManipulatorTarget() {
        return panelManipulatorTarget;
    }

    public void setClimberTarget(String _target) {
        climberTarget = _target;
    }

    public String getClimberTarget() {
        return climberTarget;
    }

    public void setConfiguration_Start() {
        currentConfiguration = Configuration.Start;
        ElevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_Travel() {
        currentConfiguration = Configuration.Travel;
        ElevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_IntakePanel() {
        currentConfiguration = Configuration.IntakePanel;
        ElevatorTarget = "IntakePanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "Out";
        climberTarget = "Stow";
    }

    public void setConfiguration_LowPanel() {
        currentConfiguration = Configuration.LowPanel;
        ElevatorTarget = "LowPanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_MidPanel() {
        currentConfiguration = Configuration.MidPanel;
        ElevatorTarget = "MidPanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_HighPanel() {
        currentConfiguration = Configuration.HighPanel;
        ElevatorTarget = "HighPanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_IntakeBall() {
        currentConfiguration = Configuration.IntakeBall;
        ElevatorTarget = "IntakeBall";
        wristTarget = "Perpendicular";
        ballManipulatorTarget = "Intake";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_LowBall() {
        currentConfiguration = Configuration.LowBall;
        ElevatorTarget = "Low";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_MidBall() {
        currentConfiguration = Configuration.MidBall;
        ElevatorTarget = "MidBall";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_HighBall() {
        currentConfiguration = Configuration.HighBall;
        ElevatorTarget = "HighBall";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_CargoBall() {
        currentConfiguration = Configuration.CargoBall;
        ElevatorTarget = "CargoBall";
        wristTarget = "CargoDiagonal";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_L2Climb() {
        currentConfiguration = Configuration.L2Climb;
        ElevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "L2";
    }

    public void setConfiguration_L3Climb() {
        currentConfiguration = Configuration.L3Climb;
        ElevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "L3";
    }

    public void setPartialConfiguration_HoldBall() {
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
    }

    public void setPartialConfiguration_ScoreBall() {
        ballManipulatorTarget = "Outtake";
    }

    public void setPartialConfiguration_ScorePanel() {
        panelManipulatorTarget = "Out";
    }

    public void update(double dt) {

        if (mSubsystemManager.mElevator.currentState == ElevatorState.MOVING) {
            tempWristTarget = "Parallel";
            tempBallManipulatorTarget = "Hold";
            tempClimberTarget = "Stow";
        } else if (mSubsystemManager.mElevator.currentState == ElevatorState.HOLDING
                    && mSubsystemManager.mWrist.currentState == WristState.MOVING) {
            tempBallManipulatorTarget = "Hold";
            tempClimberTarget = "Stow";
        }
    } 
}
