package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.DrivePlanner;

import frc.robot.behavior.TeleopMaster;

import frc.lib.utils.Utilities;

import java.util.HashMap;

public class TestMaster extends TeleopMaster {

private SubsystemManager mSubsystemManager;
private InputManager mInputManager;
private DrivePlanner mDrivePlanner;

private HashMap<String, Boolean> mButtons;
private HashMap<String, Double> mJoysticks;

    public TestMaster(SubsystemManager _subsystemManager, InputManager _inputManager) {
        super(_subsystemManager, _inputManager);
        mSubsystemManager = _subsystemManager;
        mInputManager = _inputManager;
        mDrivePlanner = mSubsystemManager.mDrivePlanner;
        mButtons = mInputManager.mButtons;
        mJoysticks = mInputManager.mJoysticks;
    }

    public void update(double dt) {
        if (mButtons.get("0a")) {
            mSubsystemManager.mWrist.setTarget("Perpendicular");
        } else {
            mSubsystemManager.mWrist.setTarget("Parallel"); 
        }
    }
}