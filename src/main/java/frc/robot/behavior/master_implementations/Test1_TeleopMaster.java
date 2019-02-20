package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveModule;
import frc.robot.InputManager;

import frc.robot.behavior.TeleopMaster;

import frc.utils.Utilities;

import static frc.robot.subsystems.BallManipulator.BallManipulatorState;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANError;
import com.revrobotics.ControlType;

public class Test1_TeleopMaster extends TeleopMaster {

    SwerveModule currentMotor;
    String output_extra;

    double k_deadband = 0.05;


    public Test1_TeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) {
        super(subsystem_manager, input_manager);

        currentMotor = mSubsystemManager.mDrivetrain.lb;
        output_extra = "lb: ";
     }

    public void update(double dt) {
        
        double referencePoint = 0;
        CANError x = CANError.kOK; boolean xx = true;
        if (mInputManager.m_buttons.get("0a")) {
            System.out.println("Setpoint = 0");
            referencePoint = 0;
            x = mSubsystemManager.mElevator.elevatorController.setReference(0, ControlType.kSmartMotion, 0);
        } else if (mInputManager.m_buttons.get("0x")) {
            System.out.println("Setpoint = 115");
            referencePoint = 115;
            x = mSubsystemManager.mElevator.elevatorController.setReference(115, ControlType.kSmartMotion, 0);
        }  else if (mInputManager.m_buttons.get("0y")) {
            referencePoint = 190;
            System.out.println("Setpoint = 190");
            x = mSubsystemManager.mElevator.elevatorController.setReference(190, ControlType.kSmartMotion, 0);
        } else if (mInputManager.m_buttons.get("0b")) {
            System.out.println("Manual");
            double joy_input = Utilities.handleDeadband(-mInputManager.m_joysticks.get("0ry"), .1);
            final double up_percentage = 1.0;
            final double down_percentage = 0.5;
            mSubsystemManager.mElevator.elevatorMotor.set(joy_input * (joy_input > 0 ? up_percentage : down_percentage));
            xx = false;
        } else {
            System.out.println("Stop Motor");
            mSubsystemManager.mElevator.elevatorMotor.set(0.0);
            xx = false;
        }

        System.out.println("CANError: " +( xx ? x.name() : "nothing"));
        if (mInputManager.m_buttons.get("0d_up")) {
        mSubsystemManager.mWrist.wristMotor.set(ControlMode.PercentOutput, mInputManager.m_joysticks.get("0ly"));
        } else if (mInputManager.m_buttons.get("0d_down")) {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.MotionMagic, 0);
        }


        if (mInputManager.m_buttons.get("0d_up")) {
        } else if (mInputManager.m_buttons.get("0d_down")) {
        } else if (mInputManager.m_buttons.get("0d_left")) {
        } else if (mInputManager.m_buttons.get("0d_right")) {
        } else {
        }

        if (mInputManager.m_buttons.get("0l_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.IN);
        } else if (mInputManager.m_buttons.get("0r_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.OUT);
        } else if (mInputManager.m_buttons.get("0l_trigger")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.HOLDING);
        }
        
        if (mInputManager.m_buttons.get("0r_trigger")) {
            mSubsystemManager.mElevator.elevatorEncoder.setPosition(0);
            mSubsystemManager.mWrist.wristMotor.setSelectedSensorPosition(0);
        }
        
        System.out.println("Position: " + mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        System.out.println("Velocity: " + mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        System.out.println("Wrist: " + mSubsystemManager.mWrist.wristMotor.getSelectedSensorPosition());
        System.out.println("-----------------");

    }
}
