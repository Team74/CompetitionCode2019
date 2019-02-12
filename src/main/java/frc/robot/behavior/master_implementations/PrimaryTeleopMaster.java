package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.StateMachine;
import frc.robot.DrivePlanner;

import frc.robot.behavior.TeleopMaster;

import frc.utils.Utilities;

import java.util.HashMap;

public class PrimaryTeleopMaster extends TeleopMaster {
    
    private StateMachine m_stateMachine;
    private DrivePlanner m_drivePlanner;

    private HashMap<String, Boolean> m_buttons;
    private HashMap<String, Double> m_joysticks;

    private double kDeadband = 0.05;

    private double lx;
    private double ly;
    private double rx;

    private double gyroVal;

    String setPointName = "";
    boolean isBall = true;   //true means ball

    public PrimaryTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) { 
        super(subsystem_manager, input_manager); 
        m_stateMachine = m_subsystem_manager.m_statemachine;
        m_drivePlanner = m_subsystem_manager.m_driveplanner;
        m_buttons = m_input_manager.m_buttons;
        m_joysticks = m_input_manager.m_joysticks;
    }

    public void update(double dt) {
        //Handle the swerve drive
        lx = m_joysticks.get("0lx");
        ly = m_joysticks.get("0ly");
        rx = m_joysticks.get("0rx");

        lx = Utilities.handleDeadband(lx, kDeadband);
        ly = Utilities.handleDeadband(ly, kDeadband);
        rx = Utilities.handleDeadband(rx, kDeadband);

        m_drivePlanner.speed = Math.hypot(lx, ly);
        m_drivePlanner.angle = Math.atan2(ly, lx);
        m_drivePlanner.rotation = rx/30;

        gyroVal = m_subsystem_manager.m_drivetrain.gyro.getAngle();
        gyroVal *= Math.PI / 180;

        m_drivePlanner.angle -= gyroVal;

        m_drivePlanner.angle %= 2*Math.PI;
        m_drivePlanner.angle += (m_drivePlanner.angle < -Math.PI) ? 2*Math.PI : 0;
        m_drivePlanner.angle -= (m_drivePlanner.angle > Math.PI) ? 2*Math.PI : 0;

        isBall = true; // get trigger

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
                    m_stateMachine.elevatorSetpoint = setPointName;
                    break;
                }
                setPointName += isBall ? "_Ball" : "_Panel";
                m_stateMachine.elevatorSetpoint = setPointName;
        }

        m_stateMachine.wristSetpoint = "Perpendicular";
        if(m_buttons.get("1d_up")) {
            m_stateMachine.ballManipulatorTarget = "Out";
            if(setPointName == "INTAKE_BALL" || setPointName.contains("Cargo")) {
                m_stateMachine.wristSetpoint = "CargoDiagonal";
            }
        } else if(m_buttons.get("1d_down")) {
            if(setPointName == "INTAKE_BALL") {
                m_stateMachine.wristSetpoint = "Parallel";
                m_stateMachine.ballManipulatorTarget = "In";
            } else {
                m_stateMachine.ballManipulatorTarget = "Hold";
            }
        } else {
            m_stateMachine.ballManipulatorTarget = "Hold";
        }

        m_stateMachine.panelManipulatorTarget = m_buttons.get("1r_bumper") ? "Out" : "In";
        //make that a toggle if we want; for now, hold it down
    }
}
