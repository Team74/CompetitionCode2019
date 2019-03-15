//TODO: Add PIDF coefficents updater
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
    public final boolean kSensorPhase = false;

    public final double kP = 0.0;
    public final double kI = 0.0;
    public final double kD = 0.0;
    public final double kF = 0.0;


    public final int kMaxVel = 0;
    public final int kMaxAccel = 0;

    public double kCurrentLimit;
    public boolean kCurrentFlag;

    public HashMap<String, Integer> kClimberSetpoints = new HashMap<String, Integer>();

    public int currentTarget;

    public ClimberState currentState;

    public static enum ClimberState {
        MOVING,
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

        climberMotor.setSelectedSensorPosition(0, kSlotIDX, kTimeoutMs);
        climberMotor.setSensorPhase(kSensorPhase);

        climberMotor.config_kP(kSlotIDX, kP, kTimeoutMs);
        climberMotor.config_kI(kSlotIDX, kI, kTimeoutMs);
        climberMotor.config_kD(kSlotIDX, kD, kTimeoutMs);
        climberMotor.config_kF(kSlotIDX, kF, kTimeoutMs);

        climberMotor.configMotionAcceleration(kMaxAccel, kTimeoutMs);
        climberMotor.configMotionCruiseVelocity(kMaxVel, kTimeoutMs);

        setSetpoints();
    }

    public void setSetpoints() {
        kClimberSetpoints.put("Stow", 0);
        kClimberSetpoints.put("L2", 0);
        kClimberSetpoints.put("L3", 0);
    }

    public void setTarget(String _target) {
        currentTarget = kClimberSetpoints.get(_target);
    }

    public void updateState() {
        if (Math.abs(currentTarget - climberMotor.getSelectedSensorPosition(kSlotIDX)) <= kHoldingDeadzone) {
            currentState = ClimberState.HOLDING;
        } else {
            currentState = ClimberState.MOVING;
        }
    }


    public void update(double dt) {
        updateState();
        climberMotor.set(ControlMode.MotionMagic, currentTarget);
    }
}