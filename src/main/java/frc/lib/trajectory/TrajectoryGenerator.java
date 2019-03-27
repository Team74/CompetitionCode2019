package frc.lib.trajectory;

import frc.robot.SubsystemManager;
import frc.robot.DrivePlanner;

import frc.lib.trajectory.Trajectory;
import frc.lib.trajectory.timing.TimedState;
import frc.lib.trajectory.timing.CentripitalAccelerationConstraint;
import frc.lib.trajectory.timing.TimingConstraints;

import frc.lib.utils.geometry.Pose2d;
import frc.lib.utils.geometry.Pose2dWithCurvature;
import frc.lib.utils.geometry.Translation2d;
import frc.lib.utils.geometry.Rotation2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
This is the class we will use to generate inital trajectories we want to follow.
It will contain all the functions and variables neccessary to generate those paths, ie. waypoints
Paths will be stored in a subclass
*/

public class TrajectoryGenerator {

    private static final double kMaxVelocity = 0.0;//inches per second
    private static final double kMaxAcceleration = 0.0;//inches per second^2
    private static final double kMaxDeceleration = 0.0;
    private static final double kMaxCentripetalAccelerationElevatorDown = 0.0;//iniches per seconds^2 Possibly define this value as a scalar based on elevator height?
    private static final double kMaxCentripetalAcceleration = 0.0;
    private static final double kMaxVoltage = 0.0;//Volts
    private static final double kDefaultVelocity = 0.0;
    private static final int kSlowdownChunks = 1;
    
    private final SubsystemManager mSubsystemManager;
    private Trajectories mTrajectories = null;
    

    public TrajectoryGenerator(SubsystemManager _subsystemManager) {
        mSubsystemManager = _subsystemManager;
    }

    public void generateTrajectory() {
        if (mTrajectories == null) {
            System.out.println("Generating trajectories... ");
            mTrajectories = new Trajectories();
            System.out.println("Finished generating trajectories");
        }
    }

    public Trajectories getTrajectories() {
        return mTrajectories;
    } 

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
        return mSubsystemManager.mDrivePlanner.generateTrajectory(
            _revearsed,
            _waypoints, 
            _constraints, 
            _maxVelocity, 
            _maxAcceleration,
            _maxDeceleration,
            _maxVoltage,
            _defaultVelocity,
            _slowdownChunks);
    }

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
        return mSubsystemManager.mDrivePlanner.generateTrajectory(_revearsed,
        _waypoints, 
        _constraints, 
        _startVelocity, 
        _endVelocity, 
        _maxVelocity, 
        _maxAcceleration, 
        _maxDeceleration,
        _maxVoltage,
        _defaultVelocity,
        _slowdownChunks);
    }
    */
    /*
    List of important positions
    Origin is the center of the alliance station wall
    If you are standing in on the origin facing the center of the field, you would be at point (0,0) with heading 0
    To your left would be positive y, straight ahead would be positive x
    x and y are defined in inches
    */

    //TODO: adjust these so positions are correct and rework how some of the crossline points are calculated.
    public static final Pose2d kLeftStart = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));
    public static final Pose2d kCenterStart = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));
    public static final Pose2d kRightStart = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));

    public static final Pose2d kLeftHighStart = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));
    public static final Pose2d kRightHighStart = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));

    public static final Pose2d kCrossLine = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));

    public static final Pose2d kLeftCrossLine = kLeftStart.transformBy(kCrossLine);
    public static final Pose2d kCenterCrossLine = kCenterStart.transformBy(kCrossLine);
    public static final Pose2d kRightCrossLine = kRightStart.transformBy(kCrossLine);

    public class Trajectories {

        public final Trajectory<TimedState<Pose2dWithCurvature>> leftCrossLine;
        public final Trajectory<TimedState<Pose2dWithCurvature>> centerCrossLine;
        public final Trajectory<TimedState<Pose2dWithCurvature>> rightCrossLine;

        private Trajectories() {
            leftCrossLine = getLeftCrossLine();
            centerCrossLine = getCenterCrossLine();
            rightCrossLine = getRightCrossLine();

        }

        //TODO: kMaxDeceleration can be changed for differant paths
        //      kDefaultVelocity can be changed for differant paths
        //      kSlowdownChunks can be changed for differant paths
        private Trajectory<TimedState<Pose2dWithCurvature>> getLeftCrossLine() {
            List<Pose2d> waypoints = new ArrayList<>();
            waypoints.add(kLeftStart);
            waypoints.add(kLeftCrossLine);
            return generateTrajectory(false, waypoints, Arrays.asList(new CentripitalAccelerationConstraint(kMaxCentripetalAccelerationElevatorDown)),
            kMaxVelocity, kMaxAcceleration, kMaxDeceleration, kMaxVoltage, 0, 1);
        }

        private Trajectory<TimedState<Pose2dWithCurvature>> getCenterCrossLine() {
            List<Pose2d> waypoints = new ArrayList<>();
            waypoints.add(kCenterStart);
            waypoints.add(kCenterCrossLine);
            return generateTrajectory(false, waypoints, Arrays.asList(new CentripitalAccelerationConstraint(kMaxCentripetalAccelerationElevatorDown)),
                    kMaxVelocity, kMaxAcceleration, kMaxDeceleration, kMaxVoltage, 0, 1);
        }

        private Trajectory<TimedState<Pose2dWithCurvature>> getRightCrossLine() {
            List<Pose2d> waypoints = new ArrayList<>();
            waypoints.add(kRightStart);
            waypoints.add(kRightCrossLine);
            return generateTrajectory(false, waypoints, Arrays.asList(new CentripitalAccelerationConstraint(kMaxCentripetalAccelerationElevatorDown)),
                    kMaxVelocity, kMaxAcceleration, kMaxDeceleration, kMaxVoltage, 0, 1);
        }
    }



}