package frc.robot.subsystems;

import frc.robot.SubsystemManager;
import frc.robot.RobotMap;
import frc.robot.Updateable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ClimberPuller implements Updateable {

    private SubsystemManager mSubsystemManager;
    private RobotMap mRobotMap;

    public TalonSRX pullerMotor;

    private final double kPullingPower = 1.0;
    private final double kUnwindingPower = -0.2;
    private final double kCurrentLimit = 0.0;

    public boolean isAccross = false;

    public boolean currentFlag = false;

    private PullerState currentState = PullerState.HOLDING;

    public static enum PullerState {
        PULLING,
        UNWINDING,
        HOLDING;
    }

    public void setState(PullerState _state) {
        currentState = _state;
    }

    public PullerState getState() {
        return currentState;
    }

    public ClimberPuller(SubsystemManager _subsystemManager, RobotMap _robotMap) {
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;

        pullerMotor = mRobotMap.Puller_0;
    }

    private boolean checkCurrent() {
        if (Math.abs(pullerMotor.getOutputCurrent()) >= kCurrentLimit || currentFlag == true) {
            currentFlag = true;
            return true;
        } else {
            return false;
        }
    }

    public void update(double dt) {
        switch(currentState) {
            case PULLING:
                if (checkCurrent()) {
                    setState(PullerState.HOLDING);
                } else {
                    pullerMotor.set(ControlMode.PercentOutput, kPullingPower);
                }
                break;
            case UNWINDING:
                pullerMotor.set(ControlMode.PercentOutput, kUnwindingPower);
            case HOLDING:
                pullerMotor.set(ControlMode.PercentOutput, 0.0);
                break;
            default:
                pullerMotor.set(ControlMode.PercentOutput, 0.0);
        }
    }

}