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

    private HashMap<String, Boolean> m_buttons;
    private HashMap<String, Double> m_joysticks;

    private double kDeadband = 0.05;

    private double lx;
    private double ly;
    private double rx;

    String setPointName = "";
    boolean isBall = true;   //true means ball

    public PrimaryTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) { 
        super(subsystem_manager, input_manager); 
        mStateMachine = mSubsystemManager.mStateMachine;
        mDrivePlanner = mSubsystemManager.mDrivePlanner;
        m_buttons = mInputManager.m_buttons;
        m_joysticks = mInputManager.m_joysticks;

        currentMotor = mSubsystemManager.mDrivetrain.lb;
        output_extra = "lb: ";
    }

    public void update(double dt) {
        if(mInputManager.m_buttons.get("0a")) {
            currentMotor = mSubsystemManager.mDrivetrain.lb;
            output_extra = "lb: ";
        }
        else if(mInputManager.m_buttons.get("0x")) {
            currentMotor = mSubsystemManager.mDrivetrain.lf;
            output_extra = "lf: ";
        }        
        else if(mInputManager.m_buttons.get("0y")) {
            currentMotor = mSubsystemManager.mDrivetrain.rf;
            output_extra = "rf: ";
        }       
        else if(mInputManager.m_buttons.get("0b")) {
            currentMotor = mSubsystemManager.mDrivetrain.rb;
            output_extra = "rb: ";
        }        
        double encoderCount = currentMotor.rotate_motor.getSelectedSensorPosition(0);
        double encoderVelocity = currentMotor.rotate_motor.getSelectedSensorVelocity(0);
        System.out.println(output_extra + encoderCount);

        if (m_buttons.get("0back")) {
            System.out.println("Gyro reset");
            mSubsystemManager.mDrivetrain.gyro.reset();
        }

        if (m_buttons.get("0l_trigger") && !m_buttons.get("0r_trigger")) {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.Low);
        } else if (m_buttons.get("0r_trigger") && !m_buttons.get("0l_trigger")) {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.High);
        } else if (m_buttons.get("0l_trigger") && m_buttons.get("0r_trigger")) {
            System.out.println("Screw you");
        } else {
            mDrivePlanner.setSpeed(DrivePlanner.Speed.Mid);
        }
        if (m_buttons.get("0d_down") || m_buttons.get("0d_up") || m_buttons.get("0d_right") || m_buttons.get("0d_left")) {
            if(m_buttons.get("0d_down")) {
                mDrivePlanner.angle = Math.PI;
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            } else if (m_buttons.get("0d_up")) {
                mDrivePlanner.angle = 0;
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            } else if (m_buttons.get("0d_right")) {
                mDrivePlanner.angle = (Math.PI / 2);
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            } else if (m_buttons.get("0d_left")) {
                mDrivePlanner.angle = -(Math.PI / 2);
                mDrivePlanner.speed = .5;
                mDrivePlanner.rotation = 0;
            }
        } else {
            //Handle the swerve drive
            lx = m_joysticks.get("0lx");
            ly = m_joysticks.get("0ly");
            rx = m_joysticks.get("0rx");

            lx = Utilities.handleDeadband(lx, kDeadband);
            ly = Utilities.handleDeadband(ly, kDeadband);
            rx = Utilities.handleDeadband(rx, kDeadband);

            mDrivePlanner.swerve(lx, ly, rx);

            
        }
        /*
        if (mInputManager.m_buttons.get("1l_trigger")) {
            isBall = true;
        } else if (mInputManager.m_buttons.get("1r_trigger")) {
            isBall = false;
        }

        switch(1) {
            case 1:
                if (m_buttons.get("1a")){
                    setPointName = "Low";
                } else if (m_buttons.get("1x")) {
                    setPointName = "Mid";
                } else if (m_buttons.get("1y")) {
                    setPointName = "High";
                } else if (m_buttons.get("1b")) {
                    setPointName = "Cargo";
                } else {
                    if(m_buttons.get("1d_down")) {
                        setPointName = "INTAKE_BALL";
                    }
                    mStateMachine.elevatorSetpoint = setPointName;
                    break;
                }
                setPointName += isBall ? "_Ball" : "_Panel";
                mStateMachine.elevatorSetpoint = setPointName;
        }

        mStateMachine.wristSetpoint = "Perpendicular";
        if(m_buttons.get("1d_up")) {
            mStateMachine.ballManipulatorTarget = "Out";
            if(setPointName == "INTAKE_BALL" || setPointName.contains("Cargo")) {
                mStateMachine.wristSetpoint = "CargoDiagonal";
            }
        } else if(m_buttons.get("1d_down")) {
            if(setPointName == "INTAKE_BALL") {
                mStateMachine.wristSetpoint = "Parallel";
                mStateMachine.ballManipulatorTarget = "In";
            } else {
                mStateMachine.ballManipulatorTarget = "Hold";
            }
        } else {
            mStateMachine.ballManipulatorTarget = "Hold";
        }

        mStateMachine.panelManipulatorTarget = m_buttons.get("1r_bumper") ? "Out" : "In";
        //make that a toggle if we want; for now, hold it down
        */
    }
}
