package frc.lib.sims;

import frc.robot.RobotMap;
import frc.robot.subsystems.BallManipulator;

public class BallManipulatorSim extends BallManipulator {

    private RobotMap mRobotMap;

    private double oldTarget = 0.0;

    public BallManipulatorSim(RobotMap _robotMap) {
        super(_robotMap);
        mRobotMap = _robotMap;
    }

    public void sim(double _target){
        if (oldTarget != _target) {
        }
    }
}