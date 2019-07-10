package frc.robot.drive;

import frc.robot.Constants;
import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import frc.lib.trajectory.*;
import frc.lib.trajectory.timing.*;
import frc.lib.utils.Utilities;
import frc.lib.utils.geometry.*;

import java.util.List;
import java.util.ArrayList;


public class DrivePlanner {
    private static DrivePlanner kInstance = null;

    private final SubsystemManager mSubsytemManager = SubsystemManager.getInstance();
    public Drivetrain mDrivetrain = Drivetrain.getInstance();

    //For trajectory generating
    private double defaultCook = 0.5;
    private boolean useDefaultCook = true;

    //For auto pathfollowing
    private static final double kMaxDx = 2.0;
    private static final double kMaxDy = .25;
    private static final double kMaxDTheta = Math.toRadians(5.0);

    //Used in controlling the rotation
    private double rotationStartTime = 0.0;
    private double rotationEndTime = 0.0;
    private double rotationTimeDuration = 0.0;
    private double targetHeading = 0.0;
    private double currentHeading = 0.0;
    private double headingDifferential = 0.0;

    private Translation2d followingCenter = Translation2d.identity();

    TrajectoryIterator<TimedState<Pose2dWithCurvature>> currentTrajectory;
    boolean isReversed = false;
    double lastTime = Double.POSITIVE_INFINITY;
    public TimedState<Pose2dWithCurvature> setpoint = new TimedState<>(Pose2dWithCurvature.identity());
    Pose2d error=  Pose2d.identity();
    Translation2d output = Translation2d.identity();

    double dt = 0.0;

    public static DrivePlanner getInstance() {
        if (kInstance == null) {
            kInstance = new DrivePlanner();
        }
        return kInstance;

    }

    private DrivePlanner() {

    }

    public void setTrajectory(final TrajectoryIterator<TimedState<Pose2dWithCurvature>> _trajectory) {
        currentTrajectory = _trajectory;
        setpoint = _trajectory.getState();
        defaultCook = _trajectory.trajectory().defaultVelocity();
        for (int i = 0; i < currentTrajectory.trajectory().length(); ++i) {
            if (currentTrajectory.trajectory().getState(i).velocity() > Utilities.kEpsilon) {
                isReversed = false;
            } else if (currentTrajectory.trajectory().getState(i).velocity() < -Utilities.kEpsilon) {
                isReversed = true;
                break;
            }
        }
    }

    public void reset() {
        error = Pose2d.identity();
        output = Translation2d.identity();
        lastTime = Double.POSITIVE_INFINITY;
        useDefaultCook = true;
    }
    
    //Generate a trajectory with starting and ending velocity 0
    public Trajectory<TimedState<Pose2dWithCurvature>> generateTrajectory(
        boolean _revearsed, 
        final List<Pose2d> _waypoints, 
        final List<TimingConstraints<Pose2dWithCurvature>> _constraints,
        double _maxVelocity, // inches/s
        double _maxAcceleration, // inches/s^2
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
            boolean _reversed,
            final List<Pose2d> _waypoints,
            final List<TimingConstraints<Pose2dWithCurvature>> _constraints,
            double _startVelocity,
            double _endVelocity,
            double _maxVelocity, // inches/s^2
            double _maxAcceleration, // inches/s^2
            double _maxDeceleration,
            double _maxVoltage,
            double _defaultVelocity,
            int _slowdownChunks){
        List<Pose2d> waypointsMaybeFlipped = _waypoints;
        //TODO: Make sure the flip constant is consistent with the way we define our cordinate system
        final Pose2d flip = Pose2d.fromRotation(new Rotation2d(0, -1, false));
        if (_reversed) {
            waypointsMaybeFlipped = new ArrayList<>(_waypoints.size());
            for (int i = 0; i < _waypoints.size(); ++i) {
                waypointsMaybeFlipped.add(_waypoints.get(i).transformBy(flip));
            }
        }

        //Create a trajectory from the spline waypoints
        Trajectory<Pose2dWithCurvature> trajectory = TrajectoryUtil.trajectoryFromSplineWaypoints(waypointsMaybeFlipped, kMaxDx, kMaxDy, kMaxDTheta);

        if (_reversed) {
            List<Pose2dWithCurvature> flipped = new ArrayList<>(trajectory.length());
            for (int i = 0; i < trajectory.length(); ++i) {
                Pose2dWithCurvature stateI = trajectory.getState(i);
                flipped.add(new Pose2dWithCurvature(stateI.getPose().transformBy(flip), -stateI.getCurvature(), stateI.getDCurvatureDs()));
            }

            trajectory = new Trajectory<>(flipped);
        }

        List<TimingConstraints<Pose2dWithCurvature>> allConstraints = new ArrayList<>();

        if (_constraints != null) {
            allConstraints.addAll(_constraints);
        }

        //Generate the timed trajectory
        Trajectory<TimedState<Pose2dWithCurvature>> timedTrajectory = TimingUtil.timeParameterizeTrajectory(_reversed, new DistanceView<>(trajectory), kMaxDx, allConstraints,
                _startVelocity, _endVelocity, _maxVelocity, _maxAcceleration, _maxDeceleration, _slowdownChunks);
        timedTrajectory.setDefaultVelocity(_defaultVelocity / Constants.kRobotMaxVelocity);
        return timedTrajectory;
    }

    public void setTargetHeading(double _targetHeading, double _rotationTimeDuration, Pose2d _robotPose) {
        double currentRobotHeading = _robotPose.getRotation().getUnboundedDegrees();
        targetHeading = Utilities.placeInAppropriate0To360Scope(currentRobotHeading, _targetHeading);
        headingDifferential = targetHeading - currentRobotHeading;
        rotationStartTime = currentTrajectory.getProgress();
        rotationEndTime = rotationStartTime + _rotationTimeDuration;
        rotationTimeDuration = _rotationTimeDuration;
    }

    public double getHeading() {
        return currentHeading;
    }

    //The following center releative to the robots center
    public void setFollowingCenter(Translation2d _followingCenter) {
        followingCenter = _followingCenter;
    }



    public void update(double dt) {
        //prob nothing here
    }
}