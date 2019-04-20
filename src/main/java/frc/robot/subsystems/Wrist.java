package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Wrist {
    public static Wrist kInstance = null;

    private final TalonSRX wristMotor = RobotMap.getInstance().Wrist_0;

    private ControlMode controlMode = ControlMode.MotionMagic;

    private double targetAngle = 0.0;
    private int targetPosition = 0;
    private int targetVelocity = 0;
    private double targetPercent = 0.0;

    public static Wrist getInstance() {
        if (kInstance == null) {
            kInstance = new Wrist();
        }
        return kInstance;
    }

    private Wrist() {
        wristMotor.setNeutralMode(NeutralMode.Brake);

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

    
    public void setControlMode(ControlMode _controlMode) {
        controlMode = _controlMode;
    }

    public void zeroEncoder() {
        wristMotor.setSelectedSensorPosition(0);
    }

    public int getPosition() {
        return wristMotor.getSelectedSensorPosition(0);
    }

    public int getVelocity() {
        return wristMotor.getSelectedSensorVelocity(0);
    }

    public double getCurrent() {
        return wristMotor.getOutputCurrent();
    }

    //Implemented stop motors in the TalonSRXOverride class, figure out why it doesn't work
    public void stopMotors() {
        wristMotor.stopMotor();
    }

    public void setAngle(double _newAngle) {
        targetAngle = _newAngle;
    }

    public void setSpeed(int _newSpeed) {
        targetVelocity = _newSpeed;
    }

    public void setPosition(int _newPosition) {
        targetPercent = _newPosition;
    }

    public void zeroAngle() {
        wristMotor.setSelectedSensorPosition(0);
    }

    public void start() {
        wristMotor.setSelectedSensorPosition(Constants.kStartingPosition);
    }

    public void stop() {
        wristMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void output() {
        switch (controlMode) {
            case PercentOutput:
                wristMotor.set(controlMode, targetPercent);
                break;
            case Position:
                wristMotor.set(controlMode, targetPosition);
                break;
            case Velocity:
                wristMotor.set(controlMode, targetVelocity);
                break;
            case MotionMagic:
                wristMotor.set(controlMode, targetPosition);
                break;
            default:
                System.out.println("Desired Wrist ControlMode not found; defaulting");
                wristMotor.set(ControlMode.PercentOutput, targetPercent);
                break;
        }
    }
}