package frc.robot.subsystems;

import frc.robot.Updateable;
import frc.robot.SubsystemManager;
import frc.robot.RobotMap;

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

public class Elevator implements Subsystem {


    public static enum ElevatorState {
        HOLDING, MOVING, MANUAL;
    }

    public SubsystemManager mSubsystemManager;
    public RobotMap mRobotMap;  //reference to the original

    public CANSparkMax elevatorMotor;
    public CANEncoder elevatorEncoder;
    public CANPIDController elevatorController;

    public double kP, kI, kD, kIZ, kF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedError;
    public int kSlotIDX = 0;

    public HashMap<String, Double> kElevatorSetpoints = new HashMap<String, Double>();

    public double currentTarget;

    public double kHoldingDeadzone = 0.0;

    public ElevatorState currentState;
    private boolean isManual = false;

    public Elevator(SubsystemManager _subsystemManager, RobotMap _robotMap){
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;

        elevatorMotor = mRobotMap.Elevator_0;
        elevatorMotor.restoreFactoryDefaults();
        elevatorMotor.setInverted(true);
        elevatorMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

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

        kP = 0.00005;
        kI = 0.0;
        kD = 0.0;
        kF = 0.000156;

        elevatorController.setIZone(kIZ, kSlotIDX);
        elevatorController.setOutputRange(kMinOutput, kMaxOutput, kSlotIDX);
        elevatorController.setSmartMotionMaxVelocity(maxVel, kSlotIDX);
        elevatorController.setSmartMotionMinOutputVelocity(minVel, kSlotIDX);
        elevatorController.setSmartMotionMaxAccel(maxAcc, kSlotIDX);
        elevatorController.setSmartMotionAllowedClosedLoopError(allowedError, kSlotIDX);

        setSetpoints();
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

    public void setSetpoints() {
        kElevatorSetpoints.put("Bottom", 0.0);

        kElevatorSetpoints.put("IntakeBall", 0.0);
        kElevatorSetpoints.put("Low_Ball", 0.0);
        kElevatorSetpoints.put("Mid_Ball", 109.0);
        kElevatorSetpoints.put("High_Ball", 199.0);
        kElevatorSetpoints.put("Cargo_Ball", 115.0);

        kElevatorSetpoints.put("Low_Panel", 40.0);
        kElevatorSetpoints.put("Mid_Panel", 132.0);
        kElevatorSetpoints.put("High_Panel", 0.0);
        }

    public void setManual(boolean _temp) {
        isManual = _temp;
    }

    public boolean isManual() {
        return isManual;
    }
    
    public void setTarget(String _target) {
        currentTarget = kElevatorSetpoints.get(_target);
        System.out.println("Elevator Target Set:  " + _target + "  @height  " + currentTarget);

    }

    public void updateState() {
        if (Math.abs(currentTarget - elevatorEncoder.getPosition()) <= kHoldingDeadzone) {
            currentState = ElevatorState.HOLDING;
            //System.out.println("Elevator State Set: " + ElevatorState.HOLDING);
        } else {
            currentState = ElevatorState.MOVING;
        }
    }

    public void update(double dt) {
        updateState();
        /*
        if (!getIsManual()) {
            elevatorController.setReference(currentTarget, ControlType.kSmartMotion, kSlotIDX);
            elevatorController.setReference(value, ctrl, pidSlot, arbFeedforward)
        }
        */
    }
}