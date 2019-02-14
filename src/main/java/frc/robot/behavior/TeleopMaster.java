package frc.robot.behavior;

import frc.robot.behavior.Master;
import frc.robot.SubsystemManager;
import frc.robot.InputManager;

abstract public class TeleopMaster extends Master {

    protected InputManager mInputManager;

    protected TeleopMaster(SubsystemManager subsystem_manager, InputManager _input_manager) {
        super(subsystem_manager);
        mInputManager = _input_manager;
    }
}
