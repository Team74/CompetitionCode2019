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
    public double kP, kI, kD, kIZ, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, maxAcc;
    public int kSlotIDX;

    public double[] listedSetpoints;
    public HashMap<String, Integer> listedSetpoints_aliases;
    public int currentTarget;

    public ElevatorState currentState;

    public Elevator(SubsystemManager _subsystemManager, RobotMap _robotMap){
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;

        elevatorMotor = mRobotMap.Elevator_0;
        elevatorEncoder = mRobotMap.Elevator_E_0;
        elevatorEncoder.setPosition(0);

        elevatorController = elevatorMotor.getPIDController();

        kIZ = 0.0;
        kMaxOutput = 1.0;
        kMinOutput = -1.0;
        maxRPM = 0.0;
        maxVel = 0.0;
        maxAcc = 0.0;

        elevatorController.setIZone(kIZ);
        elevatorController.setOutputRange(kMinOutput, kMaxOutput);
        elevatorController.setSmartMotionMaxVelocity(maxVel, kSlotIDX);
        elevatorController.setSmartMotionMaxAccel(maxAcc, kSlotIDX);

        elevatorMotor.burnFlash();
    }

    private void updatePIDFCoefficents() {
        double _p = mSubsystemManager.mDashboard.elevatorP.getDouble(kP);
        double _i = mSubsystemManager.mDashboard.elevatorI.getDouble(kI);
        double _d = mSubsystemManager.mDashboard.elevatorD.getDouble(kD);
        double _f = mSubsystemManager.mDashboard.elevatorF.getDouble(kF);

        kP = _p == kP ? kP : _p;
        kI = _i == kI ? kI : _i;
        kD = _d == kD ? kD : _d;
        kF = _f == kF ? kF : _f;

        elevatorController.setP(kP);
        elevatorController.setI(kI);
        elevatorController.setD(kD);
        elevatorController.setIZone(kIZ);
        elevatorController.setFF(kF);
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
            elevatorEncoder.setPosition(0);
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