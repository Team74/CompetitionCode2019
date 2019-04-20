package frc.robot;

import frc.robot.SubsystemManager;
import frc.robot.subsystems.Superstructure;

import frc.robot.subsystems.*;
import frc.robot.drive.*;

import org.opencv.videoio.VideoWriter;

import frc.lib.utils.Utilities;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Dashboard implements Updateable {
    private static Dashboard kInstance;

    private SubsystemManager mSubsystemManager = SubsystemManager.getInstance();
    private Superstructure mSuperstructure = Superstructure.getInstance();

    public ShuffleboardTab teleopTab;
    public ShuffleboardTab robotTuning;
    public ShuffleboardTab cameraTab;
    public ShuffleboardTab robotState;

    public NetworkTableEntry elevatorEncoder;
    public NetworkTableEntry elevatorCurrent;
    public NetworkTableEntry elevatorVelocity;

    public NetworkTableEntry elevatorP;
    public NetworkTableEntry elevatorI;
    public NetworkTableEntry elevatorD;
    public NetworkTableEntry elevatorF;

    public NetworkTableEntry wristVelocity;
    public NetworkTableEntry wristEncoder;
    public NetworkTableEntry wristCurrent;

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

    public NetworkTableEntry lfEncoder;
    public NetworkTableEntry lbEncoder;
    public NetworkTableEntry rfEncoder;
    public NetworkTableEntry rbEncoder;

    public NetworkTableEntry elevatorState;
    public NetworkTableEntry elevatorTarget;
    public NetworkTableEntry wristState;
    public NetworkTableEntry wristTarget;
    public NetworkTableEntry ballManipulatorState;

    public UsbCamera camera;

    public static Dashboard getInstance() {
        if (kInstance == null) {
            kInstance  = new Dashboard();
        }
        return kInstance;
    }

    public Dashboard(){

        teleopTab = Shuffleboard.getTab("Teleop");
        robotTuning = Shuffleboard.getTab("Robot Tuning");
        cameraTab = Shuffleboard.getTab("Camera");
        robotState = Shuffleboard.getTab("Robot State");

        Shuffleboard.selectTab("Camera");

        sanityCheck = teleopTab.add("Sanity Check", true).getEntry();

        doWeHaveBall = teleopTab.add("Do we have a ball?", mSubsystemManager.mBallManipulator.haveBall).getEntry();

        lfAngle = teleopTab.add("LF Angle", Drivetrain.getInstance().lf.currentAngle).getEntry();
        lbAngle = teleopTab.add("LB Angle", Drivetrain.getInstance().lf.currentAngle).getEntry();
        rfAngle = teleopTab.add("RF Angle", Drivetrain.getInstance().lf.currentAngle).getEntry();
        rbAngle = teleopTab.add("RB Angle", Drivetrain.getInstance().lf.currentAngle).getEntry();

        lfEncoder = teleopTab.add("LF Encoder", Drivetrain.getInstance().lf.rotate_motor.getSelectedSensorPosition(0)).getEntry();
        lbEncoder = teleopTab.add("LB Encoder", Drivetrain.getInstance().lb.rotate_motor.getSelectedSensorPosition(0)).getEntry();
        rfEncoder = teleopTab.add("RF Encoder", Drivetrain.getInstance().rf.rotate_motor.getSelectedSensorPosition(0)).getEntry();
        rbEncoder = teleopTab.add("RB Encoder", Drivetrain.getInstance().rb.rotate_motor.getSelectedSensorPosition(0)).getEntry(); 

        elevatorEncoder = robotTuning.add("Elevator Encoder",  mSuperstructure.getElevatorPosition()).getEntry();
        elevatorCurrent = robotTuning.add("Elevator Current", mSuperstructure.getElevatorCurrent()).getEntry();
        elevatorVelocity = robotTuning.add("Elevator Velocity", mSuperstructure.getElevatorVelocity()).getEntry();
        
        elevatorP = robotTuning.add("Elevator P", Constants.kElevatorP).getEntry();
        elevatorI = robotTuning.add("Elevator I", Constants.kElevatorI).getEntry();
        elevatorD = robotTuning.add("Elevator D", Constants.kElevatorD).getEntry();
        elevatorF = robotTuning.add("Elevator F", Constants.kElevatorF).getEntry();
        
        wristVelocity = robotTuning.add("Wrist Velocity", mSuperstructure.getWristVelocity()).getEntry();
        wristEncoder = robotTuning.add("Wrist Encoder", mSuperstructure.getWristPosition()).getEntry();
        wristCurrent = robotTuning.add("Wrist Current", mSuperstructure.getWristCurrent()).getEntry();

        wristP = robotTuning.add("Wrist P", Constants.kWristP).getEntry();
        wristI = robotTuning.add("Wrist I", Constants.kWristI).getEntry();
        wristD = robotTuning.add("Wrist D", Constants.kWristD).getEntry();
        wristF = robotTuning.add("Wrist F", Constants.kWristF).getEntry();
        
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

        lfEncoder.setNumber(mSubsystemManager.mDrivetrain.lf.rotate_motor.getSelectedSensorPosition(0));
        lbEncoder.setNumber(mSubsystemManager.mDrivetrain.lb.rotate_motor.getSelectedSensorPosition(0));
        rfEncoder.setNumber(mSubsystemManager.mDrivetrain.rf.rotate_motor.getSelectedSensorPosition(0));
        rbEncoder.setNumber(mSubsystemManager.mDrivetrain.rb.rotate_motor.getSelectedSensorPosition(0));        

        elevatorEncoder.setDouble(mSubsystemManager.mElevator.elevatorEncoder.getPosition());
        elevatorCurrent.setDouble(mSubsystemManager.mElevator.elevatorMotor.getOutputCurrent());
        elevatorVelocity.setDouble(mSubsystemManager.mElevator.elevatorEncoder.getVelocity());

        wristEncoder.setNumber(mSubsystemManager.mWrist.wristMotor.getSelectedSensorPosition());
        wristCurrent.setDouble(mSubsystemManager.mWrist.wristMotor.getOutputCurrent());
        wristVelocity.setNumber(mSubsystemManager.mWrist.wristMotor.getSelectedSensorVelocity(0));
        
        frontIntakeCurrent.setDouble(mSubsystemManager.mBallManipulator.mIntakeFront.getOutputCurrent());
        backIntakeCurrent.setDouble(mSubsystemManager.mBallManipulator.mIntakeBack.getOutputCurrent());

        doWeHaveBall.setBoolean(mSubsystemManager.mBallManipulator.haveBall);
    }
}