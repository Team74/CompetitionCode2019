package frc.robot;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import frc.lib.utils.geometry.*;

public class StateTracker implements Updateable {
    //Class tracks robots center of rotation reletive to the field for use in auto.
    private static StateTracker kInstance = null;

    private final SubsystemManager mSubsytemManager;
    private Pose2d previousPose;
    private Pose2d currentPose;

    private Rotation2d previousHeading;
    private Rotation2d currentHeading;

    public static StateTracker getInstance() {
        if (kInstance == null) {
            kInstance  = new StateTracker();
        }
        return kInstance;
    }

    public StateTracker(){
        mSubsytemManager = SubsystemManager.getInstance();
    }

    public Pose2d previousPose() {
        return previousPose;
    }

    public Pose2d currentPose() {
        return currentPose;
    }

    public Rotation2d previousRotation() {
        return previousHeading;
    }

    public Rotation2d currentHeading() {
        return currentHeading;
    }

    //Use encoders and gyro to localize ourself to the field
    public void update(double dt){
    }

}