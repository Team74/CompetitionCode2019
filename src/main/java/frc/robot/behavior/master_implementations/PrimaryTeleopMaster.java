package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.StateMachine;
import frc.robot.DrivePlanner;

import frc.robot.subsystems.SwerveModule;

import frc.robot.behavior.TeleopMaster;

import frc.lib.utils.Utilities;

import java.util.HashMap;

public class PrimaryTeleopMaster extends TeleopMaster {
    
    private StateMachine mStateMachine;
    private DrivePlanner mDrivePlanner;

    SwerveModule currentMotor;
    String output_extra;

    private HashMap<String, Boolean> mButtons;
    private HashMap<String, Double> mJoysticks;

    private double kDeadband = 0.05;

    private double lx;
    private double ly;
    private double rx;

    String setPointName = "";

    private boolean lastInput = false;
    private boolean isBall = true;   //true means ball

    public PrimaryTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) { 
        super(subsystem_manager, input_manager); 
        mStateMachine = mSubsystemManager.mStateMachine;
        mDrivePlanner = mSubsystemManager.mDrivePlanner;
        mButtons = mInputManager.mButtons;
        mJoysticks = mInputManager.mJoysticks;

        currentMotor = mSubsystemManager.mDrivetrain.lb;
        output_extra = "lb: ";
    }

    private void toggleIsBall(boolean _input) {
        if (_input && !lastInput) {
            isBall = !isBall;
        }
        lastInput = _input;
    }

    public void update(double dt) {
        if(mInputManager.mButtons.get("0a")) {
            currentMotor = mSubsystemManager.mDrivetrain.lb;
            output_extra = "lb: ";
            double encoderCount = currentMotor.rotate_motor.getSelectedSensorPosition(0);
            double encoderVelocity = currentMotor.rotate_motor.getSelectedSensorVelocity(0);
            System.out.println(output_extra + encoderCount);
            System.out.println(output_extra + encoderVelocity);
        }
        else if(mInputManager.mButtons.get("0x")) {
            currentMotor = mSubsystemManager.mDrivetrain.lf;
            output_extra = "lf: ";
            double encoderCount = currentMotor.rotate_motor.getSelectedSensorPosition(0);
            double encoderVelocity = currentMotor.rotate_motor.getSelectedSensorVelocity(0);
            System.out.println(output_extra + encoderCount);
            System.out.println(output_extra + encoderVelocity);
        }        
        else if(mInputManager.mButtons.get("0y")) {
            currentMotor = mSubsystemManager.mDrivetrain.rf;
            output_extra = "rf: ";
            double encoderCount = currentMotor.rotate_motor.getSelectedSensorPosition(0);
            double encoderVelocity = currentMotor.rotate_motor.getSelectedSensorVelocity(0);
            System.out.println(output_extra + encoderCount);
            System.out.println(output_extra + encoderVelocity);
        }       
        else if(mInputManager.mButtons.get("0b")) {
            currentMotor = mSubsystemManager.mDrivetrain.rb;
            output_extra = "rb: ";
            double encoderCount = currentMotor.rotate_motor.getSelectedSensorPosition(0);
            double encoderVelocity = currentMotor.rotate_motor.getSelectedSensorVelocity(0);
            System.out.println(output_extra + encoderCount);
            System.out.println(output_extra + encoderVelocity);
        }        

        if (mButtons.get("0back")) {
            System.out.println("Gyro reset");
            mSubsystemManager.mDrivetrain.gyro.reset();
        }

        if (mButtons.get("0l_trigger") && !mButtons.get("0r_trigger")) {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.Low);
        } else if (mButtons.get("0r_trigger") && !mButtons.get("0l_trigger")) {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.High);
        } else if (mButtons.get("0l_trigger") && mButtons.get("0r_trigger")) {
            System.out.println("Screw you");
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

            lx = Utilities.handleDeadband(lx, kDeadband);
            ly = Utilities.handleDeadband(ly, kDeadband);
            rx = Utilities.handleDeadband(rx, kDeadband);

            mDrivePlanner.swerve(lx, ly, rx);

            
        }
        //Handle the rest of the robot
        toggleIsBall(mButtons.get("1l_trigger"));//Set ball or panel mode
        if (isBall) {
            if (mButtons.get("1a")) {
                mStateMachine.setConfiguration_LowBall();
            } else if (mButtons.get("1x")) {
                mStateMachine.setConfiguration_MidBall();
            } else if (mButtons.get("1y")) {
                mStateMachine.setConfiguration_HighBall();
            } else if (mButtons.get("1b")) {
                mStateMachine.setConfiguration_CargoBall();
            }

            if (mButtons.get("1l_bumper")) {
                mStateMachine.setConfiguration_IntakeBall();
            } else if (mButtons.get("1r_bumper")) {
                mStateMachine.setPartialConfiguration_ScoreBall();
            }
        } else {
            if (mButtons.get("1a")) {
                mStateMachine.setConfiguration_LowPanel();
            } else if (mButtons.get("1x")) {
                mStateMachine.setConfiguration_MidPanel();
            } else if (mButtons.get("1y")) {
                mStateMachine.setConfiguration_HighPanel();
            } else if (mButtons.get("1b")) {
                mStateMachine.setConfiguration_MidPanel();
            }

            if (mButtons.get("1l_bumper")) {
                mStateMachine.setConfiguration_IntakePanel();
            } else if (mButtons.get("1r_bumper")) {
                mStateMachine.setPartialConfiguration_ScorePanel();
            }
        }

        if (mButtons.get("1start")) {
            mStateMachine.setConfiguration_Travel();
        }

        if (mButtons.get("1d_left")) {
            mStateMachine.setConfiguration_L2Climb();
        } else if (mButtons.get("1d_up")) {
            mStateMachine.setConfiguration_L3Climb();
        }

    }
}
