package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.RobotMap;

import frc.robot.Updateable;

import frc.robot.subsystems.Drivetrain;
import frc.robot.behavior.master_implementations.Paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;

public class PathFollower implements Updateable {

    public SubsystemManager mSubsystemManager;
    public RobotMap map = mSubsystemManager.mRobotMap;       // a reference -- do not try to make a new one.
    public Drivetrain drive = mSubsystemManager.mDrivetrain; // ditto
    public Paths paths = new Paths();

    public final double timeStep = 0.0;

    private SwerveModifier.Mode pathMode = SwerveModifier.Mode.SWERVE_DEFAULT;
    private Trajectory trajectory;
    private SwerveModifier modifier;

    private Trajectory lf;
    private Trajectory rf;
    private Trajectory lb;
    private Trajectory rb;

    private EncoderFollower lfFollower = new EncoderFollower();
    private EncoderFollower rfFollower = new EncoderFollower();
    private EncoderFollower lbFollower = new EncoderFollower();
    private EncoderFollower rbFollower = new EncoderFollower();

    public PathFollower(SubsystemManager subsystem_manager) {
        mSubsystemManager = subsystem_manager;
        // kP, kI, kD, 1/maxVel, acceleration gain
        lfFollower.configurePIDVA(0.0, 0.0, 0.0, 1/map.maxVel, 0);
        rfFollower.configurePIDVA(0.0, 0.0, 0.0, 1/map.maxVel, 0);
        lbFollower.configurePIDVA(0.0, 0.0, 0.0, 1/map.maxVel, 0);
        rbFollower.configurePIDVA(0.0, 0.0, 0.0, 1/map.maxVel, 0);

    }

    public void update(double dt) {
        //Using Pathfinder to follow the path
        //https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java

        double[][] swerveVectors = new double[4][2];//{ {lf_a, lf_m}, {rf_a, rf_m}, {lb_a, lb_m}, {rb_a, rf_a} }
        //!! null needs to be replaced with a get encoder position !!
        //Pathfinder.boundHalfDegrees(Pathfinder.r2d(lfFollower.getHeading())) returns a degree value on the scale of -180 to 180, We conert it to d
        /*
        swerveVectors[0][0] = boundHalfRadians(lfFollower.getHeading());
        swerveVectors[0][1] = lfFollower.calculate(null);
        swerveVectors[1][0] = boundHalfRadians(rfFollower.getHeading());
        swerveVectors[1][1] = rfFollower.calculate(null);
        swerveVectors[2][0] = boundHalfRadians(lbFollower.getHeading());
        swerveVectors[2][1] = lbFollower.calculate(null);
        swerveVectors[3][0] = boundHalfRadians(rbFollower.getHeading());
        swerveVectors[3][1] = rbFollower.calculate(null);
        */
        drive.manageModules(swerveVectors);
    }

    public void pathToTrajectory(Waypoint[] path, boolean isReversed) {
        //Set up trajectory configs
        Trajectory.Config config = new Trajectory.Config(
            Trajectory.FitMethod.HERMITE_QUINTIC, Trajectory.Config.SAMPLES_HIGH, timeStep, map.maxVel, map.maxAccel, map.maxJerk
        );
        //Create trajectory
        trajectory = Pathfinder.generate(path, config);
        //Create the modifier object
        modifier = new SwerveModifier(trajectory);
        // Generate the individual wheel trajectories using the original trajectory
        // as the centre
        modifier.modify(map.wheelBaseWidth, map.wheelBaseDepth, pathMode);

        lf = modifier.getFrontLeftTrajectory();       // Get the Front Left wheel
        rf = modifier.getFrontRightTrajectory();      // Get the Front Right wheel
        lb = modifier.getBackLeftTrajectory();        // Get the Back Left wheel
        rb = modifier.getBackRightTrajectory();       // Get the Back Right wheel
        lfFollower.setTrajectory(lf);
        rfFollower.setTrajectory(rf);
        lbFollower.setTrajectory(lb);
        rbFollower.setTrajectory(rb);
        //Null needs to be replaced with the getPosition() for encoders. Not the integrated NEOs though because they return doubles.
        /*
        lfFollower.configureEncoder(null, map.countsPerRev, map.wheelDiameter);
        rfFollower.configureEncoder(null, map.countsPerRev, map.wheelDiameter);
        lbFollower.configureEncoder(null, map.countsPerRev, map.wheelDiameter);
        rbFollower.configureEncoder(null, map.countsPerRev, map.wheelDiameter);
        */
    }


    public static double boundHalfRadians(double angleRadians) {
        while (angleRadians >= Math.PI) angleRadians -= 2*(Math.PI);
        while (angleRadians < -(Math.PI)) angleRadians += 2*(Math.PI);
        return angleRadians;
    }
}
