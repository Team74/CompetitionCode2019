package frc.robot;

/*
Essentially the 'model' class -- keeps track of the state of the robot, contains all of the advanced/composite functions used to control it, as well as containing references to all the subsystems that handle, eg, the lift, the wheels, etc. Basically, if you want to control the robot, you go through this class. There will only be one instance of SubsystemManager at a time, since it handles all the physical robot stuff.
*/

import java.util.ArrayList;
import frc.robot.Updateable;
import frc.robot.StateMachine;
import frc.robot.behavior.Master;

import frc.robot.subsystems.*;

import frc.lib.sims.*;

public class SubsystemManager implements Updateable {

    public Master m_currentMaster;  //the thing that's currently giving this thing instructions.
    private ArrayList<Updateable> mListOfUpdatingObjects = new ArrayList<Updateable>();  //this will have duplicate references from the various things below, plus others -- positionTracker, etc.

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
    public ClimberPuller mClimberPuller;
    public Climber mClimber;
    public Dashboard mDashboard;
    public ElevatorSim mElevatorSim;
    public WristSim mWristSim;
    public BallManipulatorSim mBallManipulatorSim;

    private final int kTimeoutMs = 30;

    //etc. ...
    //incidentally, do we want these to be private? Commands should go from masters through SubsystemManager, and SubsystemManager can deal with converting that into individual commands.

    SubsystemManager() {

        mRobotMap = new RobotMap(); //initializes all the physical hardware bits, but doesn't do anything further with them
        mDrivetrain = new Drivetrain(mRobotMap);
        mDrivePlanner = new DrivePlanner(this);
        mElevator = new Elevator(this, mRobotMap);
        mWrist = new Wrist(this, mRobotMap);
        mStateTracker = new StateTracker(this);
        mBallManipulator = new BallManipulator(mRobotMap);
        mPanelManipulator = new PanelManipulator(mRobotMap);
        mClimber = new Climber(this, mRobotMap);
        mClimberPuller = new ClimberPuller(this, mRobotMap);
        mDashboard = new Dashboard(this);
        mStateMachine = new StateMachine(this);

        mElevatorSim = new ElevatorSim(this, mRobotMap);
        mWristSim = new WristSim(this, mRobotMap);
        mBallManipulatorSim = new BallManipulatorSim(mRobotMap);

        mListOfUpdatingObjects.add(mStateMachine);
        mListOfUpdatingObjects.add(mDrivePlanner);
        mListOfUpdatingObjects.add(mElevator);
        mListOfUpdatingObjects.add(mWrist);
        mListOfUpdatingObjects.add(mStateTracker);
        mListOfUpdatingObjects.add(mBallManipulator);
        mListOfUpdatingObjects.add(mPanelManipulator);
        mListOfUpdatingObjects.add(mClimber);
        mListOfUpdatingObjects.add(mClimberPuller);
        mListOfUpdatingObjects.add(mDashboard);

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

    public void setIsManual(boolean _isManual) {
        mWrist.setIsManual(_isManual);
        mElevator.setIsManual(_isManual);
    }

    public boolean getIsManual() {
        if (mWrist.getIsManual() && mElevator.getIsManual()) {
            return true;
        } else {
            return false;
        }
    }

    public void zeroSuperstructureEncoders() {
        mWrist.wristMotor.setSelectedSensorPosition(0, 0, kTimeoutMs);
        mElevator.elevatorEncoder.setPosition(0.0);
    }

    public void update(double dt) {
        m_currentMaster.update(dt);//current_master, which has a reference to this object, will call the various commands below to tell the robot to do things
        for(int i = 0; i < mListOfUpdatingObjects.size(); ++i){
            mListOfUpdatingObjects.get(i).update(dt);
        }
    }

        /*
        -have lots of commands for controlling said components. Things like last year's autonAngledTurn, autonMove, or even just tankDrive.
        -NOT commands that directly interpret driver input. Let the relevant TeleopMaster deal with input directly.
        */
}
