package frc.robot.behavior;

import frc.robot.behavior.Master;
import frc.robot.SubsystemManager;

abstract public class AutonMaster extends Master {  //Nothing special here for the moment -- mostly, lets you know what type of Master a given object is
    protected AutonMaster(SubsystemManager subsystem_manager) {
        super(subsystem_manager);
    }
}
