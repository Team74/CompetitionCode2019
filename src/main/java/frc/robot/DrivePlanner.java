package frc.robot;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import frc.lib.trajectory.Trajectory;
import frc.lib.trajectory.TrajectoryEntry;
import frc.lib.trajectory.timing.TimingConstraints;

import frc.lib.utils.geometry.Pose2d;
import frc.lib.utils.geometry.Pose2dWithCurvature;

import frc.robot.subsystems.Drivetrain;

import java.util.List;


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

    //Generate a trajectory with starting and ending velocity 0
    public Trajectory<TrajectoryEntry<Pose2dWithCurvature>> generateTrajectory(
        boolean _revearsed, 
        final List<Pose2d> _waypoints, 
        final List<TimingConstraints<Pose2dWithCurvature>> _constraints,
        double _maxVelocity,
        double _maxAcceleration,
        double _maxVoltage) {
    return generateTrajectory(
        _revearsed, 
        _waypoints, 
        _constraints, 
        0.0, 
        0.0, 
        _maxVelocity, 
        _maxAcceleration, 
        _maxVoltage);
    }

    //Can generate trajectories with non 0 stating and ending velocities
    public Trajectory<TrajectoryEntry<Pose2dWithCurvature>> generateTrajectory(
            boolean _revearsed,
            final List<Pose2d> _waypoints,
            final List<TimingConstraints<Pose2dWithCurvature>> _constraints,
            double _startVelocity,
            double _endVelocity,
            double _maxVelocity,
            double _maxAcceleration,
            double _maxVoltage ){
        return
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