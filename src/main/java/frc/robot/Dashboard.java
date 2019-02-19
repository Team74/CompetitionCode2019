package frc.robot;

import frc.robot.SubsystemManager;

import frc.utils.Utilities;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Dashboard implements Updateable {

    private SubsystemManager mSubsystemManager;

    public ShuffleboardTab teleopTab;
    public ShuffleboardTab pidfTuning;

    public NetworkTableEntry elevatorEncoder;

    public NetworkTableEntry elevatorP;
    public NetworkTableEntry elevatorI;
    public NetworkTableEntry elevatorD;
    public NetworkTableEntry elevatorF;

    public NetworkTableEntry wristP;
    public NetworkTableEntry wristI;
    public NetworkTableEntry wristD;
    public NetworkTableEntry wristF;

    public NetworkTableEntry frontIntakeCurrent;
    public NetworkTableEntry backIntakeCurrent;

    public NetworkTableEntry sanityCheck;
    
    public NetworkTableEntry lfAngle;
    public NetworkTableEntry lbAngle;
    public NetworkTableEntry rfAngle;
    public NetworkTableEntry rbAngle;

    public UsbCamera camera;

    public Dashboard(SubsystemManager _subsystemManager){
        mSubsystemManager = _subsystemManager;

        teleopTab = Shuffleboard.getTab("Teleop");

        pidfTuning = Shuffleboard.getTab("Robot Tuning");

        Shuffleboard.selectTab("PIDF Tuning");


        sanityCheck = teleopTab.add("Sanity Check", true).getEntry();

        lfAngle = teleopTab.add("LF Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        lbAngle = teleopTab.add("LB Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        rfAngle = teleopTab.add("RF Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        rbAngle = teleopTab.add("RB Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();

        elevatorP = pidfTuning.add("Elevator P", mSubsystemManager.mElevator.kP).getEntry();
        elevatorI = pidfTuning.add("Elevator I", mSubsystemManager.mElevator.kI).getEntry();
        elevatorD = pidfTuning.add("Elevator D", mSubsystemManager.mElevator.kD).getEntry();
        elevatorF = pidfTuning.add("Elevator F", mSubsystemManager.mElevator.kF).getEntry();

        wristP = pidfTuning.add("Wrist P", mSubsystemManager.mWrist.kP).getEntry();
        wristI = pidfTuning.add("Wrist I", mSubsystemManager.mWrist.kI).getEntry();
        wristD = pidfTuning.add("Wrist D", mSubsystemManager.mWrist.kD).getEntry();
        wristF = pidfTuning.add("Wrist F", mSubsystemManager.mWrist.kF).getEntry();

        elevatorEncoder = pidfTuning.add("Elevator Encoder",  mSubsystemManager.mElevator.elevatorEncoder.getPosition()).getEntry();
        
        frontIntakeCurrent = pidfTuning.add("Front Current", mSubsystemManager.mBallManipulator.mIntakeFront.getOutputCurrent()).getEntry();
        backIntakeCurrent = pidfTuning.add("Back Current", mSubsystemManager.mBallManipulator.mIntakeBack.getOutputCurrent()).getEntry();

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
    }
}