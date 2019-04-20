package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Elevator {
    public static Elevator kInstance = null;

    private final CANSparkMax elevatorMotor = RobotMap.getInstance().Elevator_0;
    private final CANEncoder elevatorEncoder = RobotMap.getInstance().Elevator_E_0;
    private final CANPIDController elevatorController = elevatorMotor.getPIDController();

    private ControlType controlMode = ControlType.kSmartMotion;

    private double targetHeight = 0.0;

    public static Elevator getInstance() {
        if (kInstance == null) {
            kInstance = new Elevator();
        }
        return kInstance;
    }

    private Elevator() {
        elevatorMotor.setMotorType(MotorType.kBrushless);
        elevatorMotor.setIdleMode(IdleMode.kBrake);

        elevatorController.setP(Constants.kElevatorP, 0);
        elevatorController.setI(Constants.kElevatorI, 0);
        elevatorController.setD(Constants.kElevatorD, 0);
        elevatorController.setFF(Constants.kElevatorF, 0);

        elevatorController.setSmartMotionMaxVelocity(Constants.kElevatorMaxVelocity, 0);
        elevatorController.setSmartMotionMinOutputVelocity(0, 0);
        elevatorController.setSmartMotionMaxAccel(Constants.kElevatorMaxAcceleration, 0);
        elevatorController.setSmartMotionAllowedClosedLoopError(1, 0);
        elevatorController.setOutputRange(-1, 1, 0);
    }

    public void zeroEncoder() {
        elevatorEncoder.setPosition(0);
    }

    public double getPosition() {
        return elevatorEncoder.getPosition();
    }

    public double getVelocity() {
        return elevatorEncoder.getVelocity();
    }

    public double getCurrent() {
        return elevatorMotor.getOutputCurrent();
    }
    
    public void setControlMode(ControlType _controlType) {
        controlMode = _controlType;
    }

    public void stopMotors() {
        elevatorMotor.stopMotor();
    }

    public void zeroHeight() {
        elevatorEncoder.setPosition(0.0);
    }

    public void commandHeight(double _newHeight) {
        targetHeight = _newHeight;
    }

    public void start() {
        elevatorEncoder.setPosition(Constants.kElevatorStartingPosition);
    }

    public void stop() {
        elevatorMotor.stopMotor();
    }

    public void output() {

    }
}