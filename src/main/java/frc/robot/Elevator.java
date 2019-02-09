package frc.robot;

import frc.robot.Updateable;

import java.util.HashMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import frc.robot.RobotMap;

public class Elevator implements Updateable {

    public RobotMap mRobotMap;  //reference to the original

    public CANSparkMax elevatorMotor;
    public CANEncoder elevatorEncoder;
    public CANPIDController elevatorController;

    public double kHoldingDeadzone = 50;
    public double kP, kI, kD, kIZ, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, maxAcc;
    public int kSlotIDX;

    public double[] listedSetpoints;
    public HashMap<String, Integer> listedSetpoints_aliases;
    public int currentTarget;

    enum ElevatorState {
        HOLDING, MOVING, MANUAL
    }
    ElevatorState currentState;

    public Elevator(RobotMap robotMap){
        mRobotMap = robotMap;

        elevatorMotor = mRobotMap.Elevator_0;
        elevatorEncoder = mRobotMap.Elevator_E_0;
        elevatorController = elevatorMotor.getPIDController();

        kP = 0.0;
        kI = 0.0;
        kD = 0.0;
        kFF = 0.0;
        kIZ = 0.0;
        kMaxOutput = 1.0;
        kMinOutput = -1.0;
        maxRPM = 0.0;
        maxVel = 0.0;
        maxAcc = 0.0;

        elevatorController.setP(kP);
        elevatorController.setI(kI);
        elevatorController.setD(kD);
        elevatorController.setIZone(kIZ);
        elevatorController.setFF(kFF);
        elevatorController.setOutputRange(kMinOutput, kMaxOutput);
        elevatorController.setSmartMotionMaxVelocity(maxVel, kSlotIDX);
        elevatorController.setSmartMotionMaxAccel(maxAcc, kSlotIDX);

        elevatorMotor.burnFlash();
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

    public void update(double dT) {
        elevatorController.setReference(listedSetpoints[currentTarget], ControlType.kSmartMotion, kSlotIDX);
        if(Math.abs(listedSetpoints[currentTarget] - elevatorEncoder.getPosition()) < kHoldingDeadzone ) {//we're here)
            currentState = ElevatorState.HOLDING;
        }
    }
}