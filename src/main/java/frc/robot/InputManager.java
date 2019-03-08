package frc.robot;

/*
Class that handles *human* input; for sensors, see SensorManager. Since the various TeleopMaster subclasses are the only thing that should be touching this class, it can get updated from there.
*/

import frc.robot.Updateable;

import frc.lib.utils.Utilities;

import java.util.HashMap;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;


public class InputManager implements Updateable {

    private XboxController mController0 = new XboxController(0);
    private XboxController mController1 = new XboxController(1);

    private double kDeadband = 0.05;

    //All these are values that can be read from outside the class
    public HashMap<String, Boolean> mButtons  = new HashMap<String, Boolean>();
    public HashMap<String, Double> mJoysticks  = new HashMap<String, Double>();



    public InputManager() {
        mButtons.put("0x", false);
        mButtons.put("0y", false);
        mButtons.put("0a", false);
        mButtons.put("0b", false);
        mButtons.put("0l_trigger", false);
        mButtons.put("0r_trigger", false);
        mButtons.put("0l_bumper", false);
        mButtons.put("0r_bumper", false);
        mButtons.put("0start", mController0.getStartButton());
        mButtons.put("0back", mController0.getBackButton());
        mButtons.put("0d_down", false);
        mButtons.put("0d_up", false);
        mButtons.put("0d_left", false);
        mButtons.put("0d_right", false);


        mButtons.put("1x", false);
        mButtons.put("1y", false);
        mButtons.put("1a", false);
        mButtons.put("1b", false);
        mButtons.put("1l_trigger", false);
        mButtons.put("1r_trigger", false);
        mButtons.put("1l_bumper", false);
        mButtons.put("1r_bumper", false);
        mButtons.put("1start", mController1.getStartButton());
        mButtons.put("1back", mController1.getBackButton());
        mButtons.put("1d_down", false);
        mButtons.put("1d_up", false);
        mButtons.put("1d_left", false);
        mButtons.put("1d_right", false);

        mJoysticks.put("0lx", (double)0);
        mJoysticks.put("0ly", (double)0);
        mJoysticks.put("0rx", (double)0);
        mJoysticks.put("0ry", (double)0);
        mJoysticks.put("1lx", (double)0);
        mJoysticks.put("1ly", (double)0);
        mJoysticks.put("1rx", (double)0);
        mJoysticks.put("1ry", (double)0);
    }

    public void update(double dt) {
        mButtons.put("0x", mController0.getXButton());
        mButtons.put("0y", mController0.getYButton());
        mButtons.put("0a", mController0.getAButton());
        mButtons.put("0b", mController0.getBButton());
        mButtons.put("0l_trigger", Utilities.doubleToBool(Utilities.handleDeadband(mController0.getTriggerAxis(Hand.kLeft), kDeadband)));
        mButtons.put("0r_trigger", Utilities.doubleToBool(Utilities.handleDeadband(mController0.getTriggerAxis(Hand.kRight), kDeadband)));
        mButtons.put("0l_bumper", mController0.getBumper(Hand.kLeft));
        mButtons.put("0r_bumper", mController0.getBumper(Hand.kRight));
        mButtons.put("0start", mController0.getStartButton());
        mButtons.put("0back", mController0.getBackButton());

        mButtons.put("1x", mController1.getXButton());
        mButtons.put("1y", mController1.getYButton());
        mButtons.put("1a", mController1.getAButton());
        mButtons.put("1b", mController1.getBButton());
        mButtons.put("1l_trigger", Utilities.doubleToBool(Utilities.handleDeadband(mController1.getTriggerAxis(Hand.kLeft), kDeadband)));
        mButtons.put("1r_trigger", Utilities.doubleToBool(Utilities.handleDeadband(mController1.getTriggerAxis(Hand.kRight), kDeadband)));
        mButtons.put("1l_bumper", mController1.getBumper(Hand.kLeft));
        mButtons.put("1r_bumper", mController1.getBumper(Hand.kRight));
        mButtons.put("1start", mController1.getStartButton());
        mButtons.put("1back", mController1.getBackButton());

        mJoysticks.put("0lx", mController0.getX(Hand.kLeft));
        mJoysticks.put("0ly", mController0.getY(Hand.kLeft));
        mJoysticks.put("0rx", mController0.getX(Hand.kRight));
        mJoysticks.put("0ry", mController0.getY(Hand.kRight));

        mJoysticks.put("1lx", mController1.getX(Hand.kLeft));
        mJoysticks.put("1ly", mController1.getY(Hand.kLeft));
        mJoysticks.put("1rx", mController1.getX(Hand.kRight));
        mJoysticks.put("1ry", mController1.getY(Hand.kRight));

        if(mController0.getPOV(0) == -1) {
            mButtons.put("0d_down", false);
        } else if(mController0.getPOV(0) == 180) {
            mButtons.put("0d_down", true);
            System.out.println("POV Down");
        } else {
            mButtons.put("0d_down", false);
        }

        if(mController0.getPOV(0) == -1) {
            mButtons.put("0d_up", false);
        } else if(mController0.getPOV(0) == 0) {
            mButtons.put("0d_up", true);
        } else {
            mButtons.put("0d_up", false);
        }

        if(mController0.getPOV(0) == -1) {
            mButtons.put("0d_left", false);
        } else if(mController0.getPOV(0) == 270) {
            System.out.println("POV Left");
            mButtons.put("0d_left", true);
        } else {
            mButtons.put("0d_left", false);
        }

        if(mController0.getPOV(0) == -1) {
            mButtons.put("0d_right", false);
        } else if(mController0.getPOV(0) == 90) {
            System.out.println("POV Right");
            mButtons.put("0d_right", true);
        } else {
            mButtons.put("0d_right", false);
        }

        if(mController1.getPOV(0) == -1) {
            mButtons.put("1d_down", false);
        } else if(135 < mController1.getPOV(0) && mController1.getPOV(0) < 225) {
            mButtons.put("1d_down", true);
        } else {
            mButtons.put("1d_down", false);
        }

        if(mController1.getPOV(0) == -1) {
            mButtons.put("1d_up", false);
        } else if(mController1.getPOV(0) <= 225 && mController1.getPOV(0) >= 135) {
            mButtons.put("1d_up", true);
        } else {
            mButtons.put("1d_up", false);
        }
        if(mController1.getPOV(0) == -1) {
            mButtons.put("1d_left", false);
        } else if(225 < mController1.getPOV(0) || mController1.getPOV(0) < 315) {
            mButtons.put("1d_left", true);
        } else {
            mButtons.put("1d_left", false);
        }

        if(mController1.getPOV(0) == -1) {
            mButtons.put("1d_right", false);
        } else if(45 < mController1.getPOV(0) || mController1.getPOV(0) < 135) {
            mButtons.put("1d_right", true);
        } else {
            mButtons.put("1d_right", false);
        }
    }

}
