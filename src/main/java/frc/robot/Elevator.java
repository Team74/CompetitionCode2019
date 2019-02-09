package frc.robot;

import frc.robot.Updateable;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import frc.robot.RobotMap;

public class Elevator implements Updateable {

    public RobotMap mRobotMap;

    public CANSparkMax elevatorMotor;
    public CANEncoder elevatorEncoder;
    public CANPIDController elevatorController;

    public double setPoint;

    public double kP, kI, kD, kIZ, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, maxAcc;
    
    public int kSlotIDX;

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
    }

    public void setElevatorHeight(double _setPoint){
        setPoint = _setPoint;
        elevatorController.setReference(setPoint, ControlType.kSmartMotion, kSlotIDX);
    }

    public void update(double dT){

    }
}