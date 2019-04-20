package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants;

public class Superstructure implements Subsystem {
    //Instance of the subsystem, gettable through the getInstance() method
    private static Superstructure kInstance = null;
    private final Elevator mElevator = Elevator.getInstance();
    private final Wrist mWrist = Wrist.getInstance();
    private final BallIntake mBallIntake = BallIntake.getInstance();

    //Is the subsytem running
    private boolean isActive = false;

    public static Superstructure getInstance() {
        if (kInstance == null) {
            kInstance = new Superstructure();
        }
        return kInstance;
    }
    
    private Superstructure() {

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

    public void stopMotors() {
        mElevator.stopMotors();
        mWrist.stopMotors();
    }

    public void commandSuperstructure(double _newHeight, int _newAngle) {
        commandHeight(_newHeight);
        commandAngle(_newAngle);
    }

    public void commandHeight(double _newHeight) {
        mElevator.commandHeight(_newHeight);;
    }

    public void commandAngle(int _newAngle) {
        mWrist.commandAngle(_newAngle);
    }

    public void setIntakePower(double _power) {
        mBallIntake.setIntakePower(_power, _power);
    }

    public void setIntakePower(double _frontPower, double _backPower) {
        mBallIntake.setIntakePower(_frontPower, _backPower);
    }

    public void zeroSuperstructure() {
        mElevator.zeroHeight();
        mWrist.zeroAngle();
    }

    public void output() {
        mWrist.output();
        mElevator.output();
        mBallIntake.output();
    }

    public void update(double dt) {
        if (!isActive) {
            stopMotors();
            return;
        }
        output();
    }
}