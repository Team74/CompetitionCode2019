package frc.lib.trajectory;

import frc.robot.SubsystemManager;
import frc.robot.DrivePlanner;

import frc.lib.trajectory.Trajectory;
import frc.lib.trajectory.TrajectoryEntry;
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

    private static final double kMaxVelocity = 0.0;//in/sec
    private static final double kMaxAcceleration = 0.0;//in/sec^2
    private static final double kMaxCentripetalAccelerationElevatorDown = 0.0;//in/sec^2 Possibly define this value as a scalar based on elevator height?
    private static final double kMaxCentripetalAcceleration = 0.0;
    private static final double kMaxVoltage = 0.0;//Volts
    
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
    /*
    public Trajectory<TrajectoryEntry<Pose2dWithCurvature>> generateTrajectory(
            boolean _revearsed, 
            final List<Pose2d> _waypoints, 
            final List<TimingConstraints<Pose2dWithCurvature>> _constraints,
            double _maxVelocity,
            double _maxAcceleration,
            double _maxVoltage) {
        return mSubsystemManager.mDrivePlanner.generateTrajectory(
            _revearsed,
            _waypoints, 
            _constraints, 
            _maxVelocity, 
            _maxAcceleration,
             _maxVoltage);
    }
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
    */
    /*
    List of important positions
    Origin is the center of the alliance station wall
    If you are standing in on the origin facing the center of the field, you would be at point (0,0) with heading 0
    To your left would be positive y, straight ahead would be positive x
    x and y are defined in inches
    */
    public static final Pose2d kOrigin = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));
    public static final Pose2d kCrossLine = new Pose2d(new Translation2d(50.0, 0.0), Rotation2d.fromDegrees(0));

    public class Trajectories {
        /*
        public final Trajectory crossLine;
        
        private Trajectories() {
            crossLine = getCrossLine();
        }
        
        private Trajectory<TrajectoryEntry<Pose2dWithCurvature>> getCrossLine() {
            List<Pose2d> waypoints = new ArrayList<>();
            waypoints.add(kOrigin);
            waypoints.add(kCrossLine);
            return generateTrajectory(false, waypoints, Arrays.asList(new CentripitalAccelerationConstraint(kMaxCentripetalAccelerationElevatorDown)),
                    kMaxVelocity, kMaxAcceleration, kMaxVoltage);
        }
        */
    }



}