package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveModule;
import frc.robot.InputManager;

import frc.robot.behavior.TeleopMaster;

import frc.utils.Utilities;

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
            mSubsystemManager.mElevator.elevatorMotor.set(Utilities.handleDeadband(mInputManager.m_joysticks.get("0ly"), .1));
        } else {
            mSubsystemManager.mElevator.elevatorMotor.set(0.0);
        }
        //,mSubsystemManager.mWrist.wristMotor.set(ControlMode.MotionMagic, 0);
        System.out.println("Position: " + mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        System.out.println("Velocity: " + mSubsystemManager.mElevator.elevatorEncoder.getVelocity());
        System.out.println("-----------------");
    }
}
