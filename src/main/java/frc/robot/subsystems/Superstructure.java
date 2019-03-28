package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Superstructure implements Subsystem {
    //Instance of the subsystem, gettable through the getInstance() method
    private static Superstructure kInstance = null;
    private RobotMap mRobotMap = RobotMap.getInstance();

    //Is the subsytem running
    private boolean isActive = false;

    //Elevator height. Spark wants a double
    private double targetHeight = 0.0;
    //Wrist angle. Talon wants an int
    private int targetAngle = 0;

    private CANSparkMax elevatorMotor = mRobotMap.Elevator_0;
    private CANEncoder elevatorEncoder = mRobotMap.Elevator_E_0;
    private CANPIDController elevatorController = elevatorMotor.getPIDController();

    private TalonSRX wristMotor = mRobotMap.Wrist_0;


    public static Superstructure getInstance() {
        if (kInstance == null) {
            kInstance = new Superstructure();
        }
        return kInstance;
    }
    
    private Superstructure() {
    }

    private void configElevatorController() {
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

    private void ConfigWristController() {
        wristMotor.config_kP(0, Constants.kWristP);
        wristMotor.config_kI(0, Constants.kWristP);
        wristMotor.config_kD(0, Constants.kWristP);
        wristMotor.config_kF(0, Constants.kWristP);

        wristMotor.configMotionCruiseVelocity(Constants.kWristMaxVelocity, 10);
        wristMotor.configMotionAcceleration(Constants.kWristMaxAcceleration, 10);
        wristMotor.configPeakOutputForward(1.0, 10);
        wristMotor.configNominalOutputForward(0.0, 10);
        wristMotor.configPeakOutputReverse(0.0, 10);
        wristMotor.configNominalOutputReverse(1.0, 10);
    }

    public void start() {
        if (!isActive) {
            isActive = true;
        }
        elevatorEncoder.setPosition(Constants.kElevatorStartingPosition);
        wristMotor.setSelectedSensorPosition(Constants.kStartingPosition);

        createElevatorPID();
        createWristPID();
    }

    public void stop() {
        if (isActive) {
            isActive = false;
        }

        elevatorMotor.stopMotor();
        wristMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void commandSuperstructure(double _newHeight, int _newAngle) {
        commandHeight(_newHeight);
        commandAngle(_newAngle);
    }

    public void commandHeight(double _newHeight) {
        targetHeight = _newHeight;
    }

    public void commandAngle(int _newAngle) {
        targetAngle = _newAngle;
    }

    public void zeroSuperstructure() {
        zeroHeight();
        zeroAngle();
    }

    public void zeroHeight() {
        elevatorEncoder.setPosition(0.0);
    }

    public void zeroAngle() {
        wristMotor.setSelectedSensorPosition(0);
    }

    public void output() {
        if (!isActive) {
            return;
        }
    }
}