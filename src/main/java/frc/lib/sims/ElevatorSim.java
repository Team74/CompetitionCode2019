package frc.lib.sims;

import frc.robot.SubsystemManager;
import frc.robot.RobotMap;
import frc.robot.subsystems.Elevator;

public class ElevatorSim extends Elevator {

    private SubsystemManager mSubsystemManager;
    private RobotMap mRobotMap;

    public ElevatorSim(SubsystemManager _subsystemManager, RobotMap _robotMap) {
        super(_subsystemManager, _robotMap);
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;
    }
}