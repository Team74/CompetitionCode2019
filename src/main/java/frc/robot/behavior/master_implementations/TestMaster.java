package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.DrivePlanner;

import frc.robot.behavior.TeleopMaster;

import frc.lib.utils.Utilities;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import static frc.robot.subsystems.Climber.ClimberState;
import static frc.robot.subsystems.ClimberPuller.PullerState;

public class TestMaster extends TeleopMaster {

private SubsystemManager mSubsystemManager;
private InputManager mInputManager;
private DrivePlanner mDrivePlanner;

private HashMap<String, Boolean> mButtons;
private HashMap<String, Double> mJoysticks;

private final double kDeadband = 0.05;

    public TestMaster(SubsystemManager _subsystemManager, InputManager _inputManager) {
        super(_subsystemManager, _inputManager);
        mSubsystemManager = _subsystemManager;
        mInputManager = _inputManager;
        mDrivePlanner = mSubsystemManager.mDrivePlanner;
        mButtons = mInputManager.mButtons;
        mJoysticks = mInputManager.mJoysticks;
    }

    public void update(double dt) {
        if (mButtons.get("0l_trigger")) {
            //Update PIDF coefficents
            System.out.println("Updated PIDF Coeficents");
            mSubsystemManager.mWrist.updatePIDFCoefficents();
            mSubsystemManager.mElevator.updatePIDFCoefficents();
        }
        if (mButtons.get("0r_trigger")) {
            mSubsystemManager.zeroSuperstructureEncoders();
        }

        if (mButtons.get("0back")) {
            mSubsystemManager.setIsManual(true);
        } else if (mButtons.get("0start")) {
            mSubsystemManager.setIsManual(false);
        }

        double wristStick = -mJoysticks.get("0ry");
        wristStick = Utilities.handleDeadband(wristStick, kDeadband);
        wristStick /= 2;

        double elevatorStick = -mJoysticks.get("0ly");
        elevatorStick = Utilities.handleDeadband(elevatorStick, kDeadband);


        /*
        System.out.println("--------------------------------");
        System.out.println("Wrist Manual: " + mSubsystemManager.mWrist.getIsManual());
        System.out.println("Elevator Manual: " + mSubsystemManager.mElevator.getIsManual());
        System.out.println("Superstructure Manual: " + mSubsystemManager.getIsManual());
        */

        if (mSubsystemManager.getIsManual()) {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.PercentOutput, wristStick);
            mSubsystemManager.mElevator.elevatorMotor.set(elevatorStick);
        } else {
            if (mButtons.get("0a")) {
                mSubsystemManager.mWrist.setTarget("Parallel");
            } else if (mButtons.get("0b")) {
                mSubsystemManager.mWrist.setTarget("Perpendicular");
            } else if (mButtons.get("0x")) {
                mSubsystemManager.mWrist.setTarget("CargoDiagonal");
            } else if (mButtons.get("0y")) {
                mSubsystemManager.mWrist.setTarget("Stow");
            } else {
                
            }

            if (mButtons.get("0d_down")) {
                mSubsystemManager.mElevator.setTarget("IntakeBall");
            } else if (mButtons.get("0d_left")) {
                mSubsystemManager.mElevator.setTarget("Low_Ball");
            } else if (mButtons.get("0d_right")) {
                mSubsystemManager.mElevator.setTarget("Mid_Ball");
            } else if (mButtons.get("0d_up")) {
                mSubsystemManager.mElevator.setTarget("High_Ball");
            } else {
                
            }
        }
        /*
        if (mButtons.get("0d_up")) {
            mSubsystemManager.mClimber.setState(ClimberState.EXTENDING);
        } else if (mButtons.get("0d_down")) {
            mSubsystemManager.mClimber.setState(ClimberState.RETRACTING);
        } else {
            mSubsystemManager.mClimber.setState(ClimberState.HOLDING);
        }

        if (mButtons.get("0d_left")) {
            mSubsystemManager.mClimberPuller.setState(PullerState.PULLING);
        } else if (mButtons.get("0d_right")) {
            mSubsystemManager.mClimberPuller.setState(PullerState.UNWINDING);
        } else {
            mSubsystemManager.mClimberPuller.setState(PullerState.HOLDING);
        }
        */
    }
}