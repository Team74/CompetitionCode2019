package frc.robot.behavior;

import frc.robot.behavior.Master;
import frc.robot.SubsystemManager;
import frc.robot.InputManager;

abstract public class TeleopMaster extends Master {

    protected InputManager m_input_manager;

    protected TeleopMaster(SubsystemManager subsystem_manager, InputManager _input_manager) {
        super(subsystem_manager);
        m_input_manager = _input_manager;
    }
}
