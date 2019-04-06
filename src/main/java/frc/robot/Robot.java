package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.SubsystemManager;
import frc.robot.InputManager;
import frc.robot.Constants;
import frc.robot.behavior.master_implementations.Implemented_AutonMaster;
import frc.robot.behavior.master_implementations.PrimaryTeleopMaster;
import frc.robot.behavior.master_implementations.SimpleTeleopMaster;
import frc.robot.behavior.master_implementations.TestMaster;
import frc.robot.drive.Drivetrain;

public class Robot extends TimedRobot {

    SubsystemManager mSubsystemManager;
    InputManager mInputManager;

    Timer mTimer = new Timer();
    double dt = Constants.dt;  //no m_ just for consistency with everywhere else
    private void updateTime() {
        dt = mTimer.get();
        mTimer.reset();
    }


    public void robotInit() {
        mSubsystemManager = SubsystemManager.getInstance(); //this will set up the whole robot and its subsystems
        mInputManager = InputManager.getInstance(); //this is the significantly smaller bit of code that handles input from the xbox controllers and such
        
        mTimer.stop();
        mTimer.reset();
    }
    public void autonomousInit() {
        mSubsystemManager.setMaster(new SimpleTeleopMaster(mSubsystemManager, mInputManager));
        Drivetrain.getInstance().gyro.reset();
         //or whatever other auton we want, this will probably be chosen by a selector on the shuffleboard
         mTimer.start();
        }   
    public void autonomousPeriodic() {
        updateTime();
        mInputManager.update(dt);
        mSubsystemManager.update(dt); 
    }
    public void teleopInit() {
        mSubsystemManager.setMaster(new SimpleTeleopMaster(mSubsystemManager, mInputManager));
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