package frc.robot;

/*
Essentially the 'model' class -- keeps track of the state of the robot, contains all of the advanced/composite functions used to control it, as well as containing references to all the subsystems that handle, eg, the lift, the wheels, etc. Basically, if you want to control the robot, you go through this class. There will only be one instance of SubsystemManager at a time, since it handles all the physical robot stuff.
*/

import java.util.ArrayList;
import frc.robot.Updateable;
import frc.robot.StateMachine;
import frc.robot.behavior.Master;

import frc.robot.subsystems.*;

public class SubsystemManager implements Updateable {

    public Master m_currentMaster;  //the thing that's currently giving this thing instructions.
    private ArrayList<Updateable> m_listOfUpdatingObjects = new ArrayList<Updateable>();  //this will have duplicate references from the various things below, plus others -- positionTracker, etc.

    //all the individual subsystems
    public RobotMap mRobotMap;   //plus this one, which directly handles the physical components
    public StateMachine mStateMachine;
    public DrivePlanner mDrivePlanner;
    public Drivetrain mDrivetrain;   //this will get the RobotMap passed in when it's created
    public Elevator mElevator;
    public Wrist mWrist;
    public StateTracker mStateTracker;
    public BallManipulator mBallManipulator;
    public PanelManipulator mPanelManipulator;
    public Dashboard mDashboard;

    //etc. ...
    //incidentally, do we want these to be private? Commands should go from masters through SubsystemManager, and SubsystemManager can deal with converting that into individual commands.

    SubsystemManager() {

        mRobotMap = new RobotMap(); //initializes all the physical hardware bits, but doesn't do anything further with them
        mStateMachine = new StateMachine(this);
        mDrivePlanner = new DrivePlanner(this);
        mDrivetrain = new Drivetrain(mRobotMap);
        mElevator = new Elevator(this, mRobotMap);
        mWrist = new Wrist(this, mRobotMap);
        mStateTracker = new StateTracker(this);
        mBallManipulator = new BallManipulator(mRobotMap);
        mPanelManipulator = new PanelManipulator(mRobotMap);
        mDashboard = new Dashboard(this);

        m_listOfUpdatingObjects.add(mStateMachine);
        m_listOfUpdatingObjects.add(mDrivePlanner);
        m_listOfUpdatingObjects.add(mElevator);
        m_listOfUpdatingObjects.add(mWrist);
        m_listOfUpdatingObjects.add(mStateTracker);
        m_listOfUpdatingObjects.add(mBallManipulator);
        m_listOfUpdatingObjects.add(mPanelManipulator);
        m_listOfUpdatingObjects.add(mDashboard);

        // ... set up other Subsystems if present
        /*
        -setup all the Subsystems: Drivetrain, Grabbers, etc.
        -put them in default position -- a lot of this happened in robot.py, drive.py, and operatorFunctions in the past
        -HOWEVER: let the subsystems themselves set themselves up as much as possible. eg, have RobotMap tell liftMotor.setup() and let it handle itself
        -DON'T set up input -- that's InputManager's job
        */
        //EDIT TO THE PREVIOUS COMMENT: RobotMap should create a lot of these sorts of things, SubsystemManager sets them in a usable state
    }

    public void setCurrentMaster(Master currentMaster) {
        m_currentMaster = currentMaster;
    }

    public void update(double dt) {
        for(int i = 0; i < m_listOfUpdatingObjects.size(); ++i){
            m_listOfUpdatingObjects.get(i).update(dt);
        }
        m_currentMaster.update(dt);    //current_master, which has a reference to this object, will call the various commands below to tell the robot to do things
    }

        /*
        -have lots of commands for controlling said components. Things like last year's autonAngledTurn, autonMove, or even just tankDrive.
        -NOT commands that directly interpret driver input. Let the relevant TeleopMaster deal with input directly.
        */
}
