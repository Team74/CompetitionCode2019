package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveModule;
import frc.robot.InputManager;

import frc.robot.behavior.TeleopMaster;

import frc.lib.utils.Utilities;

import static frc.robot.subsystems.BallManipulator.BallManipulatorState;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANError;
import com.revrobotics.ControlType;

public class TestTeleopMaster extends TeleopMaster {

    SwerveModule currentMotor;
    String output_extra;

    double k_deadband = 0.05;


    public TestTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) {
        super(subsystem_manager, input_manager);

        currentMotor = mSubsystemManager.mDrivetrain.lb;
        output_extra = "lb: ";
     }

    public void update(double dt) {
        String mode = "";
        double referencePoint = 0;
        if (mInputManager.m_buttons.get("0a")) {
            mode ="Setpoint = 0";
            referencePoint = 0;
        } 
        else if (mInputManager.m_buttons.get("0x")) {
            mode ="Setpoint = 115";
            referencePoint = 115;
        }  
        else if (mInputManager.m_buttons.get("0y")) {
            referencePoint = 190;
            mode = "Setpoint = 190";
        } 
        else if (mInputManager.m_buttons.get("0b")) {
            mode = "Manual";
            double joy_input = Utilities.handleDeadband(-mInputManager.m_joysticks.get("0ry"), .1);
            mSubsystemManager.mElevator.elevatorMotor.set(joy_input);
        }

        if (mInputManager.m_buttons.get("0d_up")) {
        mSubsystemManager.mWrist.wristMotor.set(ControlMode.PercentOutput, mInputManager.m_joysticks.get("0ly"));
        } 
        else if (mInputManager.m_buttons.get("0d_down")) {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.MotionMagic, 0);
        }
        else if (mInputManager.m_buttons.get("0d_left")) {
        } 
        else if (mInputManager.m_buttons.get("0d_right")) {
        } 
        else {
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
            mSubsystemManager.mElevator.elevatorEncoder.setPosition(0);
            mSubsystemManager.mWrist.wristMotor.setSelectedSensorPosition(0);
        }
        
        System.out.println("Position: " + mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        System.out.println("Velocity: " + mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        System.out.println("-----------------");

    }
}
