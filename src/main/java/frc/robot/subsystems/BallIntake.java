package frc.robot.subsystems;

import frc.robot.Constants;

import frc.robot.RobotMap;
import frc.robot.subsystems.Subsystem;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class BallIntake {
    public static BallIntake kInstance = null;

    private final RobotMap mRobotMap = RobotMap.getInstance();

    private final TalonSRX frontMotor = mRobotMap.Intake_0;
    private final TalonSRX backMotor = mRobotMap.Intake_1;

    private boolean isActive = false;

    private double frontIntakePower;
    private double backIntakePower;

    public static BallIntake getInstance() {
        if (kInstance == null) {
            kInstance = new BallIntake();
        }
        return kInstance;
    }
    private BallIntake() {

    }

    public void start() {
        if (!isActive) {
            isActive = true;
        }
    }

    public void stop() {
        if (isActive) {
            isActive = false;
        }

    }

    public void setIntakePower(double _power) {
        setIntakePower(_power, _power);
    }

    public void setIntakePower(double _frontPower, double _backPower) {
        frontIntakePower = _frontPower;
        backIntakePower = _backPower;
    }

    public double frontIntakePower() {
        return frontIntakePower;
    }

    public double backIntakePower() {
        return backIntakePower;
    }

    public void output() {
        frontMotor.set(ControlMode.PercentOutput, frontIntakePower);
        backMotor.set(ControlMode.PercentOutput, backIntakePower);
    }
}