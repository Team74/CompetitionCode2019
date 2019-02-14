package frc.robot;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import frc.robot.subsystems.Drivetrain;

public class DrivePlanner implements Updateable {
    private SubsystemManager mSubsytemManager;
    public Drivetrain mDrivetrain;

    public double speed = 0.0;
    public double angle = 0.0;
    public double rotation = 0.0;

    public static enum Speed{
        Low, Mid, High;
    }

    public DrivePlanner(SubsystemManager subsystemManager){
        mSubsytemManager = subsystemManager;
    }

    public void setSpeed(Speed _speed){
        switch(_speed){
            case Low:
                mSubsytemManager.mDrivetrain.lf.kMaxVel = 1000;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = 1000;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = 1000;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = 1000;
            case Mid:
                mSubsytemManager.mDrivetrain.lf.kMaxVel = 2000;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = 2000;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = 2000;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = 2000;
            case High:
                mSubsytemManager.mDrivetrain.lf.kMaxVel = 3000;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = 3000;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = 3000;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = 3000;
            default:
                mSubsytemManager.mDrivetrain.lf.kMaxVel = 2000;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = 2000;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = 2000;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = 2000;

        }
    }

    public void update(double dt){
        mSubsytemManager.mDrivetrain.setMove(speed, angle, rotation);
    }
}