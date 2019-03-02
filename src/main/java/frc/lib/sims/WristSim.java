package frc.lib.sims;

import frc.robot.SubsystemManager;
import frc.robot.RobotMap;
import frc.robot.subsystems.Wrist;

public class WristSim extends Wrist {

    private SubsystemManager mSubsystemManager;
    private RobotMap mRobotMap;

    private double oldTarget = 0.0;

    public WristSim(SubsystemManager _subsystemManager, RobotMap _robotMap) {
        super(_subsystemManager, _robotMap);
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;
    }

    public void sim(double _currentTarget, double dt) {
        if(_currentTarget != oldTarget) {
            super.currentState = WristState.MOVING;
        }

    }
}