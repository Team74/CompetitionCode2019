package frc.robot;

import frc.robot.SubsystemManager;

import static frc.robot.RobotMap.isElevatorDown;
import static frc.robot.RobotMap.isWristUp;

import frc.lib.utils.Utilities;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Dashboard implements Updateable {

    private SubsystemManager mSubsystemManager;

    public ShuffleboardTab teleopTab;
    public ShuffleboardTab robotTuning;
    public ShuffleboardTab cameraTab;
    public ShuffleboardTab robotState;

    public NetworkTableEntry elevatorEncoder;
    public NetworkTableEntry elevatorCurrent;
    public NetworkTableEntry testElevatorSetpoint;

    public NetworkTableEntry elevatorP;
    public NetworkTableEntry elevatorI;
    public NetworkTableEntry elevatorD;
    public NetworkTableEntry elevatorF;

    public NetworkTableEntry testWristSetpoint;

    public NetworkTableEntry wristP;
    public NetworkTableEntry wristI;
    public NetworkTableEntry wristD;
    public NetworkTableEntry wristF;

    public NetworkTableEntry frontIntakeCurrent;
    public NetworkTableEntry backIntakeCurrent;

    public NetworkTableEntry wristLimit;
    public NetworkTableEntry elevatorLimit;

    public NetworkTableEntry doWeHaveBall;

    public NetworkTableEntry sanityCheck;
    
    public NetworkTableEntry lfAngle;
    public NetworkTableEntry lbAngle;
    public NetworkTableEntry rfAngle;
    public NetworkTableEntry rbAngle;

    public NetworkTableEntry elevatorState;
    public NetworkTableEntry elevatorTarget;
    public NetworkTableEntry wristState;
    public NetworkTableEntry wristTarget;
    public NetworkTableEntry ballManipulatorState;

    public UsbCamera camera;

    public Dashboard(SubsystemManager _subsystemManager){
        mSubsystemManager = _subsystemManager;

        teleopTab = Shuffleboard.getTab("Teleop");
        robotTuning = Shuffleboard.getTab("Robot Tuning");
        cameraTab = Shuffleboard.getTab("Camera");
        robotState = Shuffleboard.getTab("Robot State");

        Shuffleboard.selectTab("Robot Tuning");

        sanityCheck = teleopTab.add("Sanity Check", true).getEntry();

        wristLimit = teleopTab.add("Is Wrist Up?", isWristUp.get()).getEntry();
        elevatorLimit = teleopTab.add("Is Elevator Down?", isElevatorDown.get()).getEntry();

        doWeHaveBall = teleopTab.add("Do we have a ball?", mSubsystemManager.mBallManipulator.haveBall).getEntry();

        lfAngle = teleopTab.add("LF Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        lbAngle = teleopTab.add("LB Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        rfAngle = teleopTab.add("RF Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        rbAngle = teleopTab.add("RB Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();

        elevatorEncoder = robotTuning.add("Elevator Encoder",  mSubsystemManager.mElevator.elevatorEncoder.getPosition()).getEntry();
        elevatorCurrent = robotTuning.add("Elevator Current", mSubsystemManager.mElevator.elevatorMotor.getOutputCurrent()).getEntry();
        testElevatorSetpoint = robotTuning.add("Elevator Setpoint", 115.0).getEntry();

        elevatorP = robotTuning.add("Elevator P", mSubsystemManager.mElevator.kP).getEntry();
        elevatorI = robotTuning.add("Elevator I", mSubsystemManager.mElevator.kI).getEntry();
        elevatorD = robotTuning.add("Elevator D", mSubsystemManager.mElevator.kD).getEntry();
        elevatorF = robotTuning.add("Elevator F", mSubsystemManager.mElevator.kF).getEntry();

        testWristSetpoint = robotTuning.add("Wrist Setpoint", 0).getEntry();

        wristP = robotTuning.add("Wrist P", mSubsystemManager.mWrist.kP).getEntry();
        wristI = robotTuning.add("Wrist I", mSubsystemManager.mWrist.kI).getEntry();
        wristD = robotTuning.add("Wrist D", mSubsystemManager.mWrist.kD).getEntry();
        wristF = robotTuning.add("Wrist F", mSubsystemManager.mWrist.kF).getEntry();
        
        frontIntakeCurrent = robotTuning.add("Front Current", mSubsystemManager.mBallManipulator.mIntakeFront.getOutputCurrent()).getEntry();
        backIntakeCurrent = robotTuning.add("Back Current", mSubsystemManager.mBallManipulator.mIntakeBack.getOutputCurrent()).getEntry();

        /*
        elevatorState = robotState.add("Elevator State", mSubsystemManager.mElevator.currentState.name()).getEntry();
        elevatorTarget = robotState.add("Elevator Target", mSubsystemManager.mStateMachine.elevatorSetpoint).getEntry();
        wristState = robotState.add("Wrist State", mSubsystemManager.mWrist.currentState.name()).getEntry();
        wristTarget = robotState.add("Wrist Target", mSubsystemManager.mStateMachine.wristSetpoint).getEntry();
        ballManipulatorState = robotState.add("Ball Manipulator State", mSubsystemManager.mBallManipulator.currentState.name()).getEntry();
        */
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 25);// width, height, framerate
    }

    public void update(double dt){
        lfAngle.setDouble(mSubsystemManager.mDrivetrain.lf.currentAngle);
        lbAngle.setDouble(mSubsystemManager.mDrivetrain.lb.currentAngle);
        rfAngle.setDouble(mSubsystemManager.mDrivetrain.rf.currentAngle);
        rbAngle.setDouble(mSubsystemManager.mDrivetrain.rb.currentAngle);

        elevatorEncoder.setDouble(mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        
        frontIntakeCurrent.setDouble(mSubsystemManager.mBallManipulator.mIntakeFront.getOutputCurrent());
        backIntakeCurrent.setDouble(mSubsystemManager.mBallManipulator.mIntakeBack.getOutputCurrent());

        doWeHaveBall.setBoolean(mSubsystemManager.mBallManipulator.haveBall);

        elevatorCurrent.setDouble(mSubsystemManager.mElevator.elevatorMotor.getOutputCurrent());

        wristLimit.setBoolean(isWristUp.get());
        elevatorLimit.setBoolean(isElevatorDown.get());
    }
}