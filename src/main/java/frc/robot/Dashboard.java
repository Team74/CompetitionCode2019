package frc.robot;

import frc.robot.SubsystemManager;

import frc.utils.Utilities;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Dashboard implements Updateable {

    private SubsystemManager mSubsystemManager;

    public ShuffleboardTab testTab;
    public NetworkTableEntry sanityCheck;
    
    public NetworkTableEntry lfAngle;
    public NetworkTableEntry lbAngle;
    public NetworkTableEntry rfAngle;
    public NetworkTableEntry rbAngle;

    public UsbCamera camera;

    public Dashboard(SubsystemManager _subsystemManager){
        mSubsystemManager = _subsystemManager;
        testTab = Shuffleboard.getTab("Test tab");
        Shuffleboard.selectTab("Test tab");

        sanityCheck = testTab.add("Sanity Check", true).getEntry();

        lfAngle = testTab.add("LF Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        lbAngle = testTab.add("LB Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        rfAngle = testTab.add("RF Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();
        rbAngle = testTab.add("RB Angle", mSubsystemManager.mDrivetrain.lf.currentAngle).getEntry();

        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 25);// width, height, framerate
    }

    public void update(double dt){
        lfAngle.setDouble(mSubsystemManager.mDrivetrain.lf.currentAngle);
        lbAngle.setDouble(mSubsystemManager.mDrivetrain.lb.currentAngle);
        rfAngle.setDouble(mSubsystemManager.mDrivetrain.rf.currentAngle);
        rbAngle.setDouble(mSubsystemManager.mDrivetrain.rb.currentAngle);
    }
}