package frc.robot.drive;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import frc.lib.trajectory.Trajectory;
import frc.lib.trajectory.timing.TimedState;
import frc.lib.trajectory.timing.TimingConstraints;

import frc.lib.utils.geometry.*;

import frc.robot.drive.Drivetrain;

import java.util.List;
import java.util.ArrayList;


public class DrivePlanner implements Updateable {
    private SubsystemManager mSubsytemManager;
    public Drivetrain mDrivetrain;

    //For teleop swerve control
    private final int kLowSpeed = 1000;
    private final int kMidSpeed = 2500;
    private final int kHighSpeed = 5000;
    public double speed = 0.0;
    public double angle = 0.0;
    public double rotation = 0.0;
    private double gyroVal;

    //For auto pathfollowing
    private static final double kMaxDx = .25;
    private static final double kMaxDy = 2.0;
    private static final double kMaxDTheta = Math.toRadians(5.0);

    //Used in controlling the rotation
    private double rotationStartTime = 0.0;
    private double rotationEndTime = 0.0;
    private double rotationTimeDuration = 0.0;
    private double targetHeading = 0.0;
    private double currentHeading = 0.0;
    private double headingDifferential = 0.0;

    private Translation2d followingCenter = Translation2d.identity();

    public static enum Speed{
        Low, Mid, High;
    }

    public DrivePlanner(SubsystemManager subsystemManager){
        mSubsytemManager = subsystemManager;
    }
    
    //Generate a trajectory with starting and ending velocity 0
    public Trajectory<TimedState<Pose2dWithCurvature>> generateTrajectory(
        boolean _revearsed, 
        final List<Pose2d> _waypoints, 
        final List<TimingConstraints<Pose2dWithCurvature>> _constraints,
        double _maxVelocity,
        double _maxAcceleration,
        double _maxDeceleration,
        double _maxVoltage,
        double _defaultVelocity,
        int _slowdownChunks) {
    return generateTrajectory(
        _revearsed, 
        _waypoints, 
        _constraints, 
        0.0, 
        0.0, 
        _maxVelocity, 
        _maxAcceleration,
        _maxDeceleration, 
        _maxVoltage,
        _defaultVelocity,
        _slowdownChunks);
    }

    //Can generate trajectories with non 0 stating and ending velocities, also handles all trajectory inital parameterization
    public Trajectory<TimedState<Pose2dWithCurvature>> generateTrajectory(
            boolean _revearsed,
            final List<Pose2d> _waypoints,
            final List<TimingConstraints<Pose2dWithCurvature>> _constraints,
            double _startVelocity,
            double _endVelocity,
            double _maxVelocity,
            double _maxAcceleration,
            double _maxDeceleration,
            double _maxVoltage,
            double _defaultVelocity,
            int _slowdownChunks){
        List<Pose2d> waypointsMaybeFlipped = waypoints;
        //TODO: Make sure the flip constant is consistent with the way we define our cordinate system
        final Pose2d flip = Pose2d.fromRotation(new Rotation2d(0, -1, false));
        if (reversed) {
            waypointsMaybeFlipped = new ArrayList<>(waypoints.size());
            for (int i = 0; i < waypoints.size(); ++i) {
                waypointsMaybeFlipped.add(waypoints.get(i).transformby(flip));
            }
        }

        //Create a trajectory from the spline waypoints
        Trajectory<Pose2dWithCurvature> trajectory = trajec
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