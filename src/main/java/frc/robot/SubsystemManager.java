package frc.robot;

/*
Essentially the 'model' class -- keeps track of the state of the robot, contains all of the advanced/composite functions used to control it, as well as containing references to all the subsystems that handle, eg, the lift, the wheels, etc. Basically, if you want to control the robot, you go through this class. There will only be one instance of SubsystemManager at a time, since it handles all the physical robot stuff.
*/

import java.util.ArrayList;
import frc.robot.Updateable;
import frc.robot.StateMachine;
import frc.robot.behavior.Master;

import frc.robot.subsystems.*;
import frc.robot.drive.*;

public class SubsystemManager implements Updateable {
    public static SubsystemManager kInstance = null;

    public Master m_currentMaster;  //the thing that's currently giving this thing instructions.

    private ArrayList<Subsystem> mSubsystems = new ArrayList<Subsystem>();
    private ArrayList<Updateable> mDrive = new ArrayList<Updateable>();
    private ArrayList<Updateable> mSupport = new ArrayList<Updateable>();  //this will have duplicate references from the various things below, plus others -- positionTracker, etc.

    //all the individual subsystems
    private final RobotMap mRobotMap;   //plus this one, which directly handles the physical components
    public StateMachine mStateMachine;
    public StateTracker mStateTracker;
    public Dashboard mDashboard;

    private final DrivePlanner mDrivePlanner;
    private final Drivetrain mDrivetrain;
;

    private final int kTimeoutMs = 30;

    //etc. ...
    //incidentally, do we want these to be private? Commands should go from masters through SubsystemManager, and SubsystemManager can deal with converting that into individual commands.
    public static SubsystemManager getInstance() {
        if (kInstance == null) {
            kInstance = new SubsystemManager();
        }
        return kInstance;
    }

    SubsystemManager() {

        mRobotMap = RobotMap.getInstance(); //initializes all the physical hardware bits, but doesn't do anything further with them
        mStateTracker = StateTracker.getInstance();
        mDashboard = Dashboard.getInstance();
        mStateMachine = new StateMachine(this);

        mDrivetrain = Drivetrain.getInstance();
        mDrivePlanner = DrivePlanner.getInstance();

        mSupport.add(mStateMachine);
        mSupport.add(mDrivePlanner);
        mSupport.add(mStateTracker);
        mSupport.add(mDashboard);

        mDrive.add(mDrivePlanner);
        mDrive.add(mDrivetrain);

        // ... set up other Subsystems if present
        /*
        -setup all the Subsystems: Drivetrain, Grabbers, etc.
        -put them in default position -- a lot of this happened in robot.py, drive.py, and operatorFunctions in the past
        -HOWEVER: let the subsystems themselves set themselves up as much as possible. eg, have RobotMap tell liftMotor.setup() and let it handle itself
        -DON'T set up input -- that's InputManager's job
        */
        //EDIT TO THE PREVIOUS COMMENT: RobotMap should create a lot of these sorts of things, SubsystemManager sets them in a usable state
    }

    public void setMaster(Master currentMaster) {
        m_currentMaster = currentMaster;
    }

    public void update(double dt) {
        m_currentMaster.update(dt);//current_master, which has a reference to this object, will call the various commands below to tell the robot to do things
        for(int i = 0; i < mSubsystems.size(); ++i) {
            mSubsystems.get(i).update(dt);
        }

        for(int i = 0; i < mDrive.size(); ++i) {
            mDrive.get(i).update(dt);
        }
        
        for(int i = 0; i < mSupport.size(); ++i){
            mSupport.get(i).update(dt);
        }
    }

        /*
        -have lots of commands for controlling said components. Things like last year's autonAngledTurn, autonMove, or even just tankDrive.
        -NOT commands that directly interpret driver input. Let the relevant TeleopMaster deal with input directly.
        */
}
