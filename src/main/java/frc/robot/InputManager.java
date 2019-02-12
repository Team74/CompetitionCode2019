package frc.robot;

/*
Class that handles *human* input; for sensors, see SensorManager. Since the various TeleopMaster subclasses are the only thing that should be touching this class, it can get updated from there.
*/
import java.util.HashMap;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Updateable;


public class InputManager implements Updateable {

    private XboxController m_controller_0 = new XboxController(0);
    private XboxController m_controller_1 = new XboxController(1);


    //All these are values that can be read from outside the class
    public HashMap<String, Boolean> m_buttons  = new HashMap<String, Boolean>();
    public HashMap<String, Double> m_joysticks  = new HashMap<String, Double>();



    public InputManager() {
        m_buttons.put("0x", false);
        m_buttons.put("0y", false);
        m_buttons.put("0a", false);
        m_buttons.put("0b", false);
        m_buttons.put("0l_trigger", false);    //Triggers can return either a double from 0 to 1, or a boolean
        m_buttons.put("0r_trigger", false);    //We're assuming well not need the double
        m_buttons.put("0l_bumper", false);
        m_buttons.put("0r_bumper", false);
        m_buttons.put("0d_down", false);
        m_buttons.put("0d_up", false);


        m_buttons.put("1x", false);
        m_buttons.put("1y", false);
        m_buttons.put("1a", false);
        m_buttons.put("1b", false);
        m_buttons.put("1l_trigger", false);    //Triggers can return either a double from 0 to 1, or a boolean
        m_buttons.put("1r_trigger", false);    //We're assuming well not need the double
        m_buttons.put("1l_bumper", false);
        m_buttons.put("1r_bumper", false);
        m_buttons.put("1d_down", false);
        m_buttons.put("1d_up", false);

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
        //m_buttons.put("0l_trigger", m_controller_0.getTriggerAxis(Hand.kLeft));   //Not sure how to grab triggers, look into it
        //m_buttons.put("0r_trigger", m_controller_0.getTriggerAxis(Hand.kRight));  //Get trigger axis returns a double
        m_buttons.put("0l_bumper", m_controller_0.getBumper(Hand.kLeft));
        m_buttons.put("0r_bumper", m_controller_0.getBumper(Hand.kRight));

        m_buttons.put("1x", m_controller_0.getXButton());
        m_buttons.put("1y", m_controller_0.getYButton());
        m_buttons.put("1a", m_controller_0.getAButton());
        m_buttons.put("1b", m_controller_0.getBButton());
        //m_buttons.put("0l_trigger", m_controller_0.getTriggerAxis(Hand.kLeft));   //Not sure how to grab triggers, look into it
        //m_buttons.put("0r_trigger", m_controller_0.getTriggerAxis(Hand.kRight));  //Get trigger axis returns a double
        m_buttons.put("1l_bumper", m_controller_0.getBumper(Hand.kLeft));
        m_buttons.put("1r_bumper", m_controller_0.getBumper(Hand.kRight));

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
        } else if(m_controller_0.getPOV(0) <= 45 || m_controller_0.getPOV(0) >= 315) {
            m_buttons.put("0d_down", true);
        } else {
            m_buttons.put("0d_down", false);
        }

        if(m_controller_1.getPOV(0) == -1) {
            m_buttons.put("1d_down", false);
        } else if(m_controller_1.getPOV(0) <= 45 || m_controller_1.getPOV(0) >= 315) {
            m_buttons.put("1d_down", true);
        } else {
            m_buttons.put("1d_down", false);
        }

        if(m_controller_0.getPOV(0) == -1) {
            m_buttons.put("0d_up", false);
        } else if(m_controller_0.getPOV(0) <= 45 || m_controller_0.getPOV(0) >= 315) {
            m_buttons.put("0d_up", true);
        } else {
            m_buttons.put("0d_up", false);
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
