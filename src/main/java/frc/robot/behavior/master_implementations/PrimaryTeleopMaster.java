package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;

import java.util.HashMap;

import frc.robot.InputManager;
import frc.robot.StateMachine;
import frc.robot.behavior.TeleopMaster;

public class PrimaryTeleopMaster extends TeleopMaster {
    
    private StateMachine m_stateMachine;
    private HashMap<String, Boolean> m_buttons;

    String setPointName = "";
    boolean isBall = true;   //true means ball

    public PrimaryTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) { 
        super(subsystem_manager, input_manager); 
        m_stateMachine = m_subsystem_manager.m_statemachine;
        m_buttons = m_input_manager.m_buttons;
    }

    public void update(double dt) {

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
                    m_stateMachine.elevatorSetpoint = setPointName;
                    break;
                }
                setPointName += isBall ? "_Ball" : "_Panel";
                m_stateMachine.elevatorSetpoint = setPointName;
        }

        m_stateMachine.wristSetpoint = "Perpendicular";
        if(m_buttons.get("1d_up")) {
            m_stateMachine.ballManipulatorTarget = "Out";
            if(setPointName.contains("Low") || setPointName.contains("Cargo")) {
                m_stateMachine.wristSetpoint = "CargoDiagonal";
            }
        } else if(m_buttons.get("1d_down")) {
            if(setPointName.contains("Low")) {
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
