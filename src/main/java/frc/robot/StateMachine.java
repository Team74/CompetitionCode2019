package frc.robot;

import java.util.HashMap;

import frc.robot.subsystems.BallManipulator;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Wrist;

import static frc.robot.subsystems.BallManipulator.BallManipulatorState;

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
    private String elevatorTarget = "Bottom";
    private String wristTarget = "Stow";
    private String ballManipulatorTarget = "Hold";
    private String panelManipulatorTarget = "In";
    private String climberTarget = "Stow";

    public StateMachine(SubsystemManager subsystemManager) {
        mSubsystemManager = subsystemManager;
    }

    public void setElevatorTarget(String _target) {
        elevatorTarget = _target;
    }

    public String getElevatorTarget() {
        return elevatorTarget;
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
        elevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_Travel() {
        currentConfiguration = Configuration.Travel;
        elevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_IntakePanel() {
        currentConfiguration = Configuration.IntakePanel;
        elevatorTarget = "IntakePanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "Out";
        climberTarget = "Stow";
    }

    public void setConfiguration_LowPanel() {
        currentConfiguration = Configuration.LowPanel;
        elevatorTarget = "LowPanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_MidPanel() {
        currentConfiguration = Configuration.MidPanel;
        elevatorTarget = "MidPanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_HighPanel() {
        currentConfiguration = Configuration.HighPanel;
        elevatorTarget = "HighPanel";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_IntakeBall() {
        currentConfiguration = Configuration.IntakeBall;
        elevatorTarget = "IntakeBall";
        wristTarget = "Perpendicular";
        ballManipulatorTarget = "Intake";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_LowBall() {
        elevatorTarget = "Low";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_MidBall() {
        elevatorTarget = "MidBall";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_HighBall() {
        elevatorTarget = "HighBall";
        wristTarget = "Parallel";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_CargoBall() {
        elevatorTarget = "CargoBall";
        wristTarget = "CargoDiagonal";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "Stow";
    }

    public void setConfiguration_L2Climb() {
        elevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "L2";
    }

    public void setConfiguration_L3Climb() {
        elevatorTarget = "Bottom";
        wristTarget = "Stow";
        ballManipulatorTarget = "Hold";
        panelManipulatorTarget = "In";
        climberTarget = "L3";
    }

    public void setPartialConfiguration_ScoreBall() {
        ballManipulatorTarget = "Outtake";
    }

    public void setPartialConfiguration_ScorePanel() {
        panelManipulatorTarget = "Out";
    }

    public void update(double dt) {
    } 
}
