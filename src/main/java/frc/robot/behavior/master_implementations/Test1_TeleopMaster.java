package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveModule;
import frc.robot.InputManager;

import frc.robot.behavior.TeleopMaster;

import frc.utils.Utilities;

import static frc.robot.subsystems.BallManipulator.BallManipulatorState;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
        if (mInputManager.m_buttons.get("0a")) {
            mSubsystemManager.mElevator.elevatorController.setReference(0, ControlType.kSmartMotion);
        } else if (mInputManager.m_buttons.get("0x")) {
            mSubsystemManager.mElevator.elevatorController.setReference(0, ControlType.kSmartMotion);
        }  else if (mInputManager.m_buttons.get("0y")) {
            mSubsystemManager.mElevator.elevatorController.setReference(-200, ControlType.kSmartMotion);
        } else if (mInputManager.m_buttons.get("0b")) {
            mSubsystemManager.mElevator.elevatorMotor.set(Utilities.handleDeadband(mInputManager.m_joysticks.get("0ry"), .1));
        } else {
            mSubsystemManager.mElevator.elevatorMotor.set(0.0);
        }

        if (mInputManager.m_buttons.get("0d_up")) {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.MotionMagic, 0);
        } else if (mInputManager.m_buttons.get("0d_down")) {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.MotionMagic, 3190);
        } else if (mInputManager.m_buttons.get("0d_left")) {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.MotionMagic, 2000);
        } else if (mInputManager.m_buttons.get("0d_right")) {
            mSubsystemManager.mWrist.wristMotor.set(mInputManager.m_joysticks.get("0ly"));
        } else {
            mSubsystemManager.mWrist.wristMotor.set(ControlMode.MotionMagic, 0);
        }

        if (mInputManager.m_buttons.get("0l_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.IN);
        } else if (mInputManager.m_buttons.get("0r_bumper")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.OUT);
        } else if (mInputManager.m_buttons.get("0l_trigger")) {
            mSubsystemManager.mBallManipulator.setState(BallManipulatorState.HOLDING);
        }

        System.out.println("Position: " + mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        System.out.println("Velocity: " + mSubsystemManager.mElevator.elevatorEncoder.getVelocity());
        System.out.println("-----------------");
    }
}
