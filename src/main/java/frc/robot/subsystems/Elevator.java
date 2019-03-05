package frc.robot.subsystems;

import frc.robot.Updateable;
import frc.robot.SubsystemManager;
import frc.robot.RobotMap;

import static frc.robot.RobotMap.isElevatorDown;

import java.util.HashMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

/*
Elevator Setpoints
    Bottom/Climb: 0
    IntakeBall: 0
    LowBall: 20
    MidBall: 115
    HighBall: 200
    CargoBall:
    LowPanel:
    MidPanel:
    HighPanel:
*/

public class Elevator implements Updateable {


    public static enum ElevatorState {
        HOLDING, MOVING, MANUAL;
    }

    public SubsystemManager mSubsystemManager;
    public RobotMap mRobotMap;  //reference to the original

    public CANSparkMax elevatorMotor;
    public CANEncoder elevatorEncoder;
    public CANPIDController elevatorController;

    public double kHoldingDeadzone = 50;
    public double kP, kI, kD, kIZ, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedError;
    public int kSlotIDX = 0;

    public double[] listedSetpoints = new double[0];
    public HashMap<String, Integer> listedSetpoints_aliases = new HashMap<String, Integer>();
    public int currentTarget;

    public ElevatorState currentState;

    public Elevator(SubsystemManager _subsystemManager, RobotMap _robotMap){
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;

        elevatorMotor = mRobotMap.Elevator_0;
        elevatorMotor.restoreFactoryDefaults();
        elevatorMotor.setInverted(true);
        elevatorMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        elevatorEncoder = mRobotMap.Elevator_E_0;
        elevatorEncoder.setPosition(0);

        elevatorController = elevatorMotor.getPIDController();

        //Instatiate controller parameters
        kIZ = 0.0;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 0.0;
        maxVel = 5600;
        minVel = 0.0;
        maxAcc = 5600;
        allowedError = 0.0;

        kP = 0.0;
        kI = 0.0;
        kD = 0.0;
        kF = 0.000156;

        elevatorController.setIZone(kIZ, kSlotIDX);
        elevatorController.setOutputRange(kMinOutput, kMaxOutput, kSlotIDX);
        elevatorController.setSmartMotionMaxVelocity(maxVel, kSlotIDX);
        elevatorController.setSmartMotionMinOutputVelocity(minVel, kSlotIDX);
        elevatorController.setSmartMotionMaxAccel(maxAcc, kSlotIDX);
        elevatorController.setSmartMotionAllowedClosedLoopError(allowedError, kSlotIDX);

        //elevatorMotor.burnFlash();
    }

    public void updatePIDFCoefficents() {
        //Check dashboard to see if coefficents have changed
        double _p = mSubsystemManager.mDashboard.elevatorP.getDouble(kP);
        double _i = mSubsystemManager.mDashboard.elevatorI.getDouble(kI);
        double _d = mSubsystemManager.mDashboard.elevatorD.getDouble(kD);
        double _f = mSubsystemManager.mDashboard.elevatorF.getDouble(kF);

        if (kP != _p) {
            kP = _p;
            System.out.println("Changed P");
            elevatorController.setP(kP, kSlotIDX);
        } 
        if (kI != _i) {
            kI = _i;
            System.out.println("Changed I");
            elevatorController.setI(kI, kSlotIDX);
        }
        if (kD != _d) {
            kD = _d;
            System.out.println("Changed D");
            elevatorController.setD(kD, kSlotIDX);
        }
        if (kF != _f) {
            kF = _f;
            System.out.println("Changed F");
            elevatorController.setFF(kF, kSlotIDX);
        }
    }

    public void setSetpoints(String[] aliases, double[] targets) {
        if(aliases.length != targets.length) {
            throw new RuntimeException("setSetpoints received bad inputs");
        }

        for(int i = 0; i < aliases.length; ++i) {
            listedSetpoints_aliases.put(aliases[i], i);
        }

        setSetpoints(targets);
    }
    public void setSetpoints(double[] targets) {
        listedSetpoints = targets;
    }

    public void setTarget(String targetName) {
        setTarget(listedSetpoints_aliases.get(targetName));
    }
    
    public void setTarget(int target) {
        if(currentTarget != target) {
            currentTarget = target;
            currentState = ElevatorState.MOVING;
        }
    }

    public void checkLimit() {
        if (isElevatorDown.get()) {
            //elevatorEncoder.setPosition(0);
        }
    }

    public void update(double dT) {
        checkLimit();
        updatePIDFCoefficents();
        /*
        elevatorController.setReference(listedSetpoints[currentTarget], ControlType.kSmartMotion, kSlotIDX);
        if(Math.abs(listedSetpoints[currentTarget] - elevatorEncoder.getPosition()) < kHoldingDeadzone ) {//we're here)
            currentState = ElevatorState.HOLDING;
        }
        */
    }
}