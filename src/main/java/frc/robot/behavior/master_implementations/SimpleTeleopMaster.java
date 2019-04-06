package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.Constants;
import frc.robot.InputManager;

import frc.robot.behavior.TeleopMaster;
import frc.lib.utils.Utilities;

import frc.robot.drive.*;
import frc.robot.subsystems.*;

import com.ctre.phoenix.motorcontrol.ControlMode;


import java.util.HashMap;

public class SimpleTeleopMaster extends TeleopMaster {

    private final SubsystemManager mSubsystemManager;
    private final DrivePlanner mDrivePlanner;
    private final InputManager mInputManager; 

    private HashMap<String, Boolean> mButtons;
    private HashMap<String, Double> mJoysticks;

    private final double kJoystickDeadband = Constants.kJoystickDeadband;

    private double ly;
    private double lx;
    private double rx;


    public SimpleTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) {
        super();
        mSubsystemManager = SubsystemManager.getInstance();
        mDrivePlanner = DrivePlanner.getInstance();
        mInputManager = InputManager.getInstance();
        mButtons = mInputManager.mButtons;
        mJoysticks = mInputManager.mJoysticks;
     }

    public void update(double dt) {
        if (mButtons.get("0back")) {
            System.out.println("Gyro reset");
            mSubsystemManager.mDrivetrain.gyro.reset();
        }

        if (mButtons.get("0l_trigger")) {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.Low);
        } else if (mButtons.get("0r_trigger")) {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.High);
        } else {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.Mid);
        }
        if (mButtons.get("0d_down") || mButtons.get("0d_up") || mButtons.get("0d_right") || mButtons.get("0d_left")) {
            if(mButtons.get("0d_down")) {
                mDrivePlanner.angle = Math.PI;
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            } else if (mButtons.get("0d_up")) {
                mDrivePlanner.angle = 0;
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            } else if (mButtons.get("0d_right")) {
                mDrivePlanner.angle = (Math.PI / 2);
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            } else if (mButtons.get("0d_left")) {
                mDrivePlanner.angle = -(Math.PI / 2);
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            }
        } else {
            //Handle the swerve drive
            lx = mJoysticks.get("0lx");
            ly = mJoysticks.get("0ly");
            rx = mJoysticks.get("0rx");

            lx = Utilities.handleDeadband(lx, kJoystickDeadband);
            ly = Utilities.handleDeadband(ly, kJoystickDeadband);
            rx = Utilities.handleDeadband(rx, kJoystickDeadband);

            mDrivePlanner.swerve(lx, ly, rx);

            
        }
        //Handle the elevator
        double elevatorStick = -mJoysticks.get("1ly");
        elevatorStick = Utilities.handleDeadband(elevatorStick, kJoystickDeadband);
        mSubsystemManager.mElevator.elevatorMotor.set(elevatorStick);

        //Handle the wrist
        if (mButtons.get("1r_trigger")) {
            mSubsystemManager.mWrist.wristMotor.setSelectedSensorPosition(0);
        }

        double wristStick = -mJoysticks.get("1ry");
        wristStick = Utilities.handleDeadband(wristStick, kJoystickDeadband);
        wristStick /= 2;

        if (mButtons.get("1back")) {
            mSubsystemManager.mWrist.setManual(true);
        } else if (mButtons.get("1start")) {
            mSubsystemManager.mWrist.setManual(false);
        }

        if (mSubsystemManager.mWrist.isManual()) {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.PercentOutput, wristStick);
        } else {
            if (mButtons.get("1a")) {
                mSubsystemManager.mWrist.setTarget("Parallel");
            } else if (mButtons.get("1b")) {
                mSubsystemManager.mWrist.setTarget("Perpendicular");
            } else if (mButtons.get("1x")) {
                mSubsystemManager.mWrist.setTarget("CargoDiagonal");
            } else if (mButtons.get("1y")) {
                mSubsystemManager.mWrist.setTarget("Stow");
            }
        }

        //Handle the Ball Manipulator
        if (mButtons.get("1l_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.IN);
        } 
        else if (mButtons.get("1r_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.OUT);
        } 
        else if (mButtons.get("1l_trigger")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.HOLDING);
        }

        //Handle Climber
        /*
        if (mButtons.get("1d_up")) {
            mSubsystemManager.mClimber.setState(ClimberState.EXTENDING);
        } else if (mButtons.get("1d_down")) {
            mSubsystemManager.mClimber.setState(ClimberState.RETRACTING);
        } else {
            mSubsystemManager.mClimber.setState(ClimberState.HOLDING);
        }

        if (mButtons.get("1d_left")) {
            mSubsystemManager.mClimberPuller.setState(PullerState.PULLING);
        } else if (mButtons.get("1d_right")) {
            mSubsystemManager.mClimberPuller.setState(PullerState.UNWINDING);
        } else {
            mSubsystemManager.mClimberPuller.setState(PullerState.HOLDING);
        }
        */
    }
}