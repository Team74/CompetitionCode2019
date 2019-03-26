package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.util.HashMap;

public class Climber implements Updateable {

    private SubsystemManager mSubsystemManager;
    private RobotMap mRobotMap;

    public TalonSRX climberMotor;

    public final int kSlotIDX = 0;
    public final int kTimeoutMs = 30;

    public final boolean kMotorInvert = false;

    public boolean isUp = false;
    public boolean isDown = false;

    public double kCurrentLimit = Double.POSITIVE_INFINITY;
    public boolean currentFlag = false;

    private ClimberState currentState = ClimberState.HOLDING;

    private double kExtendingPower = 1.0;
    private double kRetractingPower = -0.5;

    public static enum ClimberState {
        EXTENDING,
        RETRACTING,
        HOLDING;
    }

    private final int kHoldingDeadzone = 0;

    public Climber(SubsystemManager _subsystemManager, RobotMap _robotMap) {
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;
        climberMotor = mRobotMap.Climber_0;

        climberMotor.setNeutralMode(NeutralMode.Brake);
        climberMotor.setInverted(kMotorInvert);

        climberMotor.configNominalOutputForward(0, kTimeoutMs);
		climberMotor.configNominalOutputReverse(0, kTimeoutMs);
		climberMotor.configPeakOutputForward(1, kTimeoutMs);
        climberMotor.configPeakOutputReverse(-1, kTimeoutMs);
    }

    public void setState(ClimberState _state) {
        currentState = _state;
    }

    public ClimberState getState() {
        return currentState;
    }

    private boolean checkCurrent() {
        if (Math.abs(climberMotor.getOutputCurrent()) >= kCurrentLimit || currentFlag == true) {
            currentFlag = true;
            return true;
        } else {
            return false;
        }
    }

    public void update(double dt) {
        switch(currentState) {
            case EXTENDING:
                if (checkCurrent()) {
                    setState(ClimberState.HOLDING);
                } else {
                    climberMotor.set(ControlMode.PercentOutput, kExtendingPower);
                }
                break;
            case RETRACTING:
                climberMotor.set(ControlMode.PercentOutput, kRetractingPower);
                break;
            case HOLDING:
                climberMotor.set(ControlMode.PercentOutput, 0.0);
                break;
            default:
                climberMotor.set(ControlMode.PercentOutput, 0.0);
        }
    }
}