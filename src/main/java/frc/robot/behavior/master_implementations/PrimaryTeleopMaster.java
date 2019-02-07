package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.behavior.TeleopMaster;

public class PrimaryTeleopMaster extends TeleopMaster {
    public PrimaryTeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) { super(subsystem_manager, input_manager); }

    public void update(double dt) {        
        //if(m_input_manager.buttons_pressed.get('x')) {  //PSEUDOCODE ALERT
            //m_subsystem_manager.teleopMove(1,0.5,0.75);    //These are made-up numbers, but should illustrate the point: Master's don't implement, they tell the SubsystemManager what to do
        //}
    }
}
