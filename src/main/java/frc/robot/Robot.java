package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.behavior.master_implementations.Implemented_AutonMaster;
import frc.robot.behavior.master_implementations.PrimaryTeleopMaster;
import frc.robot.behavior.master_implementations.SimpleTeleopMaster;
import frc.robot.behavior.master_implementations.TestMaster;

public class Robot extends TimedRobot {

    SubsystemManager mSubsystemManager;
    InputManager mInputManager;

    Timer mTimer = new Timer();
    double dt = 0.02;  //no m_ just for consistency with everywhere else
    private void updateTime() {
        dt = mTimer.get();
        mTimer.reset();
    }


    public void robotInit() {
        mSubsystemManager = new SubsystemManager(); //this will set up the whole robot and its subsystems
        mInputManager = new InputManager(); //this is the significantly smaller bit of code that handles input from the xbox controllers and such
        
        mTimer.stop();
        mTimer.reset();
    }
    public void autonomousInit() {
        mSubsystemManager.setCurrentMaster(new TestMaster(mSubsystemManager, mInputManager));
         //or whatever other auton we want -- we'll probably need something for SmartDashboard eventually

         mTimer.start();
        }   
    public void autonomousPeriodic() {
        updateTime();
        mInputManager.update(dt);
        mSubsystemManager.update(dt); 
    }
    public void teleopInit() {
        mSubsystemManager.setCurrentMaster(new SimpleTeleopMaster(mSubsystemManager, mInputManager));
        //similarly, if we want to do, say, a different control scheme, a different teleopmaster could be subbed in that would interpret the inputs differently
        mTimer.reset();
        mTimer.start();
    }
    public void teleopPeriodic() {
        updateTime();
        mInputManager.update(dt);
        mSubsystemManager.update(dt);
    }
}
