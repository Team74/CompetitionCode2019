package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.behavior.master_implementations.Implemented_AutonMaster;
import frc.robot.behavior.master_implementations.Test1_TeleopMaster;

public class Robot extends TimedRobot {

    SubsystemManager m_subsystem_manager;
    InputManager m_input_manager;

    Timer mTimer = new Timer();
    double dt = 0.02;  //no m_ just for consistency with everywhere else
    private void updateTime() {
        dt = mTimer.get();
        mTimer.reset();
    }


    public void robotInit() {
        m_subsystem_manager = new SubsystemManager(); //this will set up the whole robot and its subsystems
        m_input_manager = new InputManager(); //this is the significantly smaller bit of code that handles input from the xbox controllers and such

        mTimer.stop();
        mTimer.reset();
    }
    public void autonomousInit() {
        m_subsystem_manager.setCurrentMaster(new Implemented_AutonMaster(m_subsystem_manager));
         //or whatever other auton we want -- we'll probably need something for SmartDashboard eventually

         mTimer.start();
        }   
    public void autonomousPeriodic() {
        updateTime();
        m_subsystem_manager.update(dt); 
    }
    public void teleopInit() {
        m_subsystem_manager.setCurrentMaster(new Test1_TeleopMaster(m_subsystem_manager, m_input_manager));
        //similarly, if we want to do, say, a different control scheme, a different teleopmaster could be subbed in that would interpret the inputs differently
        mTimer.reset();
        mTimer.start();
    }
    public void teleopPeriodic() {
        updateTime();
        m_input_manager.update(dt);
        m_subsystem_manager.update(dt);
    }

}
