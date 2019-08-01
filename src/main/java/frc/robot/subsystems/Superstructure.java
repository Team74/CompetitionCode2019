package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.subsystems.Elevator.ElevatorControlState;
import frc.robot.subsystems.Wrist.WristControlState;

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
    
    @Override
    public void start() {
        if (!isActive) {
            isActive = true;
        }
    }

    @Override
    public void stop() {
        if (isActive) {
            isActive = false;
        }
        stopSuperstructure();
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
    public void startSuperstructure() {
        mElevator.start();
        mWrist.start();
        mBallIntake.start();
    }

    public void stopSuperstructure() {
        mElevator.stop();
        mWrist.stop();
    }

    public void commandSuperstructure(double _elevatorDemand, ElevatorControlState _elevatorControlState, double _wristDemand, WristControlState _wristControlState) {
        commandElevator(_elevatorDemand, _elevatorControlState);
        commandWrist(_wristDemand, _wristControlState);
    }

    public void commandElevator(double _elevatorDemand, ElevatorControlState _elevatorControlState) {

    }

    public void commandWrist(double _wristDemand, WristControlState _wristControlState) {

    }

    public void setIntakePower(double _power) {
        mBallIntake.setIntakePower(_power, _power);
    }

    public void setIntakePower(double _frontPower, double _backPower) {
        mBallIntake.setIntakePower(_frontPower, _backPower);
    }

    @Override
    public void zeroSensors() {
        mElevator.zeroSensors();
        mWrist.zeroSensors();
    }

    public void zeroSuperstructure() {
        mElevator.zeroHeight();
        mWrist.zeroAngle();
    }

    @Override
    public void readPeriodicInputs() {
        mElevator.readPeriodicInputs();
        mWrist.readPeriodicInputs();
        mBallIntake.readPeriodicInputs();

    }

    @Override
    public void writePeriodicOutputs() {
        mElevator.writePeriodicOutputs();
        mWrist.writePeriodicOutputs();
        mBallIntake.output();
    }

    public void update(double dt) {
        if (!isActive) {
            stopSuperstructure();
            return;
        }
        writePeriodicOutputs();
    }

    @Override
    public void outputTelemetry() {

    }
}