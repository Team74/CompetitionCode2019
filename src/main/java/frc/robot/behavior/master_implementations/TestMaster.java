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
        if (mButtons.get("0l_trigger")) {
            //Update PIDF coefficents
            mSubsystemManager.mWrist.updatePIDFCoefficents();
            mSubsystemManager.mElevator.updatePIDFCoefficents();
        }

        if (mButtons.get("0a")) {
            mSubsystemManager.mWrist.setTarget("Parallel");
        } else if (mButtons.get("0b")) {
            mSubsystemManager.mWrist.setTarget("Perpendicular");
        } else if (mButtons.get("0x")) {
            mSubsystemManager.mWrist.setTarget("CargoDiagonal");
        } else if (mButtons.get("0y")) {
            mSubsystemManager.mWrist.setTarget("Stow");
        }

        /*
        if (mButtons.get("0a")) {
            mSubsystemManager.mElevator.setTarget("IntakeBall");
        } else if (mButtons.get("0b")) {
            mSubsystemManager.mElevator.setTarget("LowBall");
        } else if (mButtons.get("0x")) {
            mSubsystemManager.mElevator.setTarget("MidBall");
        } else if (mButtons.get("0y")) {
            mSubsystemManager.mElevator.setTarget("HighBall");
        }
        */
    }
}