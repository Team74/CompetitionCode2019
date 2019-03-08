package frc.robot;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import frc.robot.subsystems.Drivetrain;

public class DrivePlanner implements Updateable {
    private SubsystemManager mSubsytemManager;
    public Drivetrain mDrivetrain;

    private final int kLowSpeed = 1000;
    private final int kMidSpeed = 2500;
    private final int kHighSpeed = 5000;

    public double speed = 0.0;
    public double angle = 0.0;
    public double rotation = 0.0;

    private double gyroVal;

    public static enum Speed{
        Low, Mid, High;
    }

    public DrivePlanner(SubsystemManager subsystemManager){
        mSubsytemManager = subsystemManager;
    }

    public void setSpeed(Speed _speed){
        switch(_speed){
            case Low:
                //System.out.println("Set Speed: Low");
                mSubsytemManager.mDrivetrain.lf.kMaxVel = kLowSpeed;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = kLowSpeed;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = kLowSpeed;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = kLowSpeed;
                break;
            case Mid:
                //System.out.println("Set Speed: Mid");
                mSubsytemManager.mDrivetrain.lf.kMaxVel = kMidSpeed;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = kMidSpeed;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = kMidSpeed;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = kMidSpeed;
                break;
            case High:
                //System.out.println("Set Speed: High");
                mSubsytemManager.mDrivetrain.lf.kMaxVel = kHighSpeed;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = kHighSpeed;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = kHighSpeed;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = kHighSpeed;
                break;
            default:
                //System.out.println("Set Speed: Default");
                mSubsytemManager.mDrivetrain.lf.kMaxVel = kMidSpeed;
                mSubsytemManager.mDrivetrain.lb.kMaxVel = kMidSpeed;
                mSubsytemManager.mDrivetrain.rf.kMaxVel = kMidSpeed;
                mSubsytemManager.mDrivetrain.rb.kMaxVel = kMidSpeed;
                break;
        }
    }

    public void swerve(double _lx, double _ly, double _rx) {
        speed = Math.hypot(_lx, _ly);
        angle = Math.atan2(_ly, _lx);
        rotation = _rx/30;

        gyroVal = mSubsytemManager.mDrivetrain.gyro.getAngle();
        gyroVal *= Math.PI / 180;

        angle -= gyroVal;

        angle %= 2*Math.PI;
        angle += (angle < -Math.PI) ? 2*Math.PI : 0;
        angle -= (angle > Math.PI) ? 2*Math.PI : 0;
        angle += Math.PI/2;
    }

    public void update(double dt) {
;        mSubsytemManager.mDrivetrain.setMove(speed, angle, rotation);
    }
}