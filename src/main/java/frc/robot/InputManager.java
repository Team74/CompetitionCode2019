package frc.robot;

/*
Class that handles *human* input; for sensors, see SensorManager. Since the various TeleopMaster subclasses are the only thing that should be touching this class, it can get updated from there.
*/

import frc.robot.Updateable;

import frc.utils.Utilities;

import java.util.HashMap;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;


public class InputManager implements Updateable {

    private XboxController m_controller_0 = new XboxController(0);
    private XboxController m_controller_1 = new XboxController(1);

    private double kDeadband = 0.05;

    //All these are values that can be read from outside the class
    public HashMap<String, Boolean> m_buttons  = new HashMap<String, Boolean>();
    public HashMap<String, Double> m_joysticks  = new HashMap<String, Double>();



    public InputManager() {
        m_buttons.put("0x", false);
        m_buttons.put("0y", false);
        m_buttons.put("0a", false);
        m_buttons.put("0b", false);
        m_buttons.put("0l_trigger", false);
        m_buttons.put("0r_trigger", false);
        m_buttons.put("0l_bumper", false);
        m_buttons.put("0r_bumper", false);
        m_buttons.put("0start", m_controller_0.getStartButton());
        m_buttons.put("0back", m_controller_0.getBackButton());
        m_buttons.put("0d_down", false);
        m_buttons.put("0d_up", false);
        m_buttons.put("0d_left", false);
        m_buttons.put("0d_right", false);


        m_buttons.put("1x", false);
        m_buttons.put("1y", false);
        m_buttons.put("1a", false);
        m_buttons.put("1b", false);
        m_buttons.put("1l_trigger", false);
        m_buttons.put("1r_trigger", false);
        m_buttons.put("1l_bumper", false);
        m_buttons.put("1r_bumper", false);
        m_buttons.put("1start", m_controller_1.getStartButton());
        m_buttons.put("1back", m_controller_1.getBackButton());
        m_buttons.put("1d_down", false);
        m_buttons.put("1d_up", false);
        m_buttons.put("1d_left", false);
        m_buttons.put("1d_right", false);

        m_joysticks.put("0lx", (double)0);
        m_joysticks.put("0ly", (double)0);
        m_joysticks.put("0rx", (double)0);
        m_joysticks.put("0ry", (double)0);
        m_joysticks.put("1lx", (double)0);
        m_joysticks.put("1ly", (double)0);
        m_joysticks.put("1rx", (double)0);
        m_joysticks.put("1ry", (double)0);
    }

    public void update(double dt) {
        m_buttons.put("0x", m_controller_0.getXButton());
        m_buttons.put("0y", m_controller_0.getYButton());
        m_buttons.put("0a", m_controller_0.getAButton());
        m_buttons.put("0b", m_controller_0.getBButton());
        m_buttons.put("0l_trigger", Utilities.doubleToBool(Utilities.handleDeadband(m_controller_0.getTriggerAxis(Hand.kLeft), kDeadband)));
        m_buttons.put("0r_trigger", Utilities.doubleToBool(Utilities.handleDeadband(m_controller_0.getTriggerAxis(Hand.kRight), kDeadband)));
        m_buttons.put("0l_bumper", m_controller_0.getBumper(Hand.kLeft));
        m_buttons.put("0r_bumper", m_controller_0.getBumper(Hand.kRight));
        m_buttons.put("0start", m_controller_0.getStartButton());
        m_buttons.put("0back", m_controller_0.getBackButton());

        m_buttons.put("1x", m_controller_0.getXButton());
        m_buttons.put("1y", m_controller_0.getYButton());
        m_buttons.put("1a", m_controller_0.getAButton());
        m_buttons.put("1b", m_controller_0.getBButton());
        m_buttons.put("1l_trigger", Utilities.doubleToBool(Utilities.handleDeadband(m_controller_1.getTriggerAxis(Hand.kLeft), kDeadband)));
        m_buttons.put("1r_trigger", Utilities.doubleToBool(Utilities.handleDeadband(m_controller_1.getTriggerAxis(Hand.kRight), kDeadband)));
        m_buttons.put("1l_bumper", m_controller_0.getBumper(Hand.kLeft));
        m_buttons.put("1r_bumper", m_controller_0.getBumper(Hand.kRight));
        m_buttons.put("1start", m_controller_1.getStartButton());
        m_buttons.put("1back", m_controller_1.getBackButton());

        m_joysticks.put("0lx", m_controller_0.getX(Hand.kLeft));
        m_joysticks.put("0ly", m_controller_0.getY(Hand.kLeft));
        m_joysticks.put("0rx", m_controller_0.getX(Hand.kRight));
        m_joysticks.put("0ry", m_controller_0.getY(Hand.kRight));
        m_joysticks.put("1lx", m_controller_1.getX(Hand.kLeft));
        m_joysticks.put("1ly", m_controller_1.getY(Hand.kLeft));
        m_joysticks.put("1rx", m_controller_1.getX(Hand.kRight));
        m_joysticks.put("1ry", m_controller_1.getY(Hand.kRight));

        if(m_controller_0.getPOV(0) == -1) {
            m_buttons.put("0d_down", false);
        } else if(135 < m_controller_0.getPOV(0) && m_controller_0.getPOV(0) < 225) {
            m_buttons.put("0d_down", true);
        } else {
            m_buttons.put("0d_down", false);
        }

        if(m_controller_0.getPOV(0) == -1) {
            m_buttons.put("0d_up", false);
        } else if(m_controller_0.getPOV(0) <= 45 || m_controller_0.getPOV(0) >= 315) {
            m_buttons.put("0d_up", true);
        } else {
            m_buttons.put("0d_up", false);
        }

        if(m_controller_0.getPOV(0) == -1) {
            m_buttons.put("0d_left", false);
        } else if(225 < m_controller_0.getPOV(0) || m_controller_0.getPOV(0) < 315) {
            m_buttons.put("0d_left", true);
        } else {
            m_buttons.put("0d_left", false);
        }

        if(m_controller_0.getPOV(0) == -1) {
            m_buttons.put("0d_right", false);
        } else if(45 < m_controller_0.getPOV(0) || m_controller_0.getPOV(0) < 135) {
            m_buttons.put("0d_right", true);
        } else {
            m_buttons.put("0d_right", false);
        }

        if(m_controller_1.getPOV(0) == -1) {
            m_buttons.put("1d_down", false);
        } else if(135 < m_controller_1.getPOV(0) && m_controller_1.getPOV(0) < 225) {
            m_buttons.put("1d_down", true);
        } else {
            m_buttons.put("1d_down", false);
        }

        if(m_controller_1.getPOV(0) == -1) {
            m_buttons.put("1d_up", false);
        } else if(m_controller_1.getPOV(0) <= 225 && m_controller_1.getPOV(0) >= 135) {
            m_buttons.put("1d_up", true);
        } else {
            m_buttons.put("1d_up", false);
        }
    }

}
