package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveModule;
import frc.robot.InputManager;

import frc.robot.behavior.TeleopMaster;

import frc.lib.utils.Utilities;

import static frc.robot.subsystems.BallManipulator.BallManipulatorState;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.ControlType;

public class TestTeleopMaster extends TeleopMaster {

    SwerveModule currentMotor;
    String output_extra;

    double k_deadband = 0.05;

    private double oldElevatorReferencePoint = Double.NEGATIVE_INFINITY;


    public TestTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) {
        super(subsystem_manager, input_manager);

        currentMotor = mSubsystemManager.mDrivetrain.lb;
        output_extra = "lb: ";
     }

    public void update(double dt) {
        /*
        double elevatorReferencePoint = 0.0;
        if (mInputManager.m_buttons.get("0a")) {
            elevatorReferencePoint = 0;
            if (elevatorReferencePoint != oldElevatorReferencePoint) {
                oldElevatorReferencePoint = elevatorReferencePoint;
                System.out.println("Set Elevator 0");
                mSubsystemManager.mElevator.elevatorController.setReference(elevatorReferencePoint, ControlType.kSmartMotion);
            }
        } 
        else if (mInputManager.m_buttons.get("0x")) {
            elevatorReferencePoint = 115;
            if (elevatorReferencePoint != oldElevatorReferencePoint) {
                oldElevatorReferencePoint = elevatorReferencePoint;
                mSubsystemManager.mElevator.elevatorController.setReference(elevatorReferencePoint, ControlType.kSmartMotion);
            }        
        }  
        else if (mInputManager.m_buttons.get("0y")) {
            mSubsystemManager.mElevator.elevatorEncoder.setPosition(0);
        } 
        else if (mInputManager.m_buttons.get("0b")) {
            double joy_input = Utilities.handleDeadband(-mInputManager.m_joysticks.get("0ly"), .1);
            System.out.println(joy_input);
            mSubsystemManager.mElevator.elevatorMotor.set(joy_input);
        } 
        else {
            mSubsystemManager.mElevator.elevatorMotor.set(0.0);
        }
        */
        int wristReferencePoint = 0;

        if (mInputManager.m_buttons.get("0y")) {
            wristReferencePoint = 3190;
            mSubsystemManager.mWrist.set(ControlMode.MotionMagic, wristReferencePoint);
        } 
        else if (mInputManager.m_buttons.get("0x")) {
            wristReferencePoint = 2000;
            mSubsystemManager.mWrist.set(ControlMode.MotionMagic, wristReferencePoint);
        } 
        else if (mInputManager.m_buttons.get("0a")) {
            wristReferencePoint = 0;
            mSubsystemManager.mWrist.set(ControlMode.MotionMagic, wristReferencePoint);
        }
        else {
            double throttle = Utilities.handleDeadband(-mInputManager.m_joysticks.get("0ry"), 0.05);
            if (throttle == 0) {
                mSubsystemManager.mWrist.currentFlag = false;
            }
            mSubsystemManager.mWrist.set(ControlMode.PercentOutput, throttle);
        }

        if (mInputManager.m_buttons.get("0l_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.IN);
        } 
        else if (mInputManager.m_buttons.get("0r_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.OUT);
        } 
        else if (mInputManager.m_buttons.get("0l_trigger")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.HOLDING);
        }
        if (mInputManager.m_buttons.get("0r_trigger")) {
            mSubsystemManager.mWrist.wristMotor.setSelectedSensorPosition(0);
        }
        /*
        System.out.println("Wrist Position: " + mSubsystemManager.mWrist.wristMotor.getSelectedSensorPosition());
        System.out.println("Wrist Velocity: " + mSubsystemManager.mWrist.elevatorEncoder.getSelectedSensorVelocity());
        System.out.println("-----------------");
        */

    }
}