package frc.robot;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import java.util.HashMap;

public class Wrist implements Updateable {

    public RobotMap mRobotMap;

    public WPI_TalonSRX wristMotor;

    public final int kPIDLoopIdx = 0;
    public final int kTimeoutMs = 30;

    public final boolean kMotorInvert = false;
    public final boolean kSensorPhase = false;

    public final double kF = 0.0;
    public final double kP = 0.0; 
    public final double kI = 0.0; 
    public final double kD = 0.0;
    public final int kMaxVel = 0;
    public final int kMaxAccel = 0;

    public final double kHoldingDeadzone = 0.0;

    public double[] listedSetpoints;
    public HashMap<String, Integer> listedSetpoints_aliases;
    public int currentTarget;

    enum WristState {
        HOLDING, MOVING, MANUAL;
    }
    public WristState currentState;

    public Wrist(RobotMap _robotMap){
        mRobotMap = _robotMap;
        wristMotor = mRobotMap.Wrist_0;

        wristMotor.configFactoryDefault(kTimeoutMs);
        
        wristMotor.setNeutralMode(NeutralMode.Brake);

        wristMotor.setInverted(kMotorInvert);

        wristMotor.configNominalOutputForward(0, kTimeoutMs);
		wristMotor.configNominalOutputReverse(0, kTimeoutMs);
		wristMotor.configPeakOutputForward(1, kTimeoutMs);
        wristMotor.configPeakOutputReverse(-1, kTimeoutMs);

        wristMotor.configAllowableClosedloopError(0, kPIDLoopIdx, kTimeoutMs);

        wristMotor.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
		wristMotor.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
		wristMotor.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
        wristMotor.config_kD(kPIDLoopIdx, kD, kTimeoutMs);

        wristMotor.configMotionCruiseVelocity(kMaxVel, kTimeoutMs);
        wristMotor.configMotionAcceleration(kMaxAccel, kTimeoutMs);

        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, kPIDLoopIdx, kTimeoutMs);//Not sure what feeback device type to use
        wristMotor.setSensorPhase(kSensorPhase);


    }

    public void setSetpoints(String[] aliases, double[] targets) {
        if(aliases.length != targets.length) {
            throw new RuntimeException("setSetpoints received bad inputs");
        }

        for(int i = 0; i < aliases.length; ++i) {
            listedSetpoints_aliases.put(aliases[i], i);
        }

        setSetpoints(targets);
    }

    public void setSetpoints(double[] targets) {
        listedSetpoints = targets;
    }

    public void setTarget(String targetName) {
        setTarget(listedSetpoints_aliases.get(targetName));
    }

    public void setTarget(int target) {
        if(currentTarget != target) {
            currentTarget = target;
            currentState = WristState.MOVING;
        }
    }

    public void update(double dT) {
        wristMotor.set(ControlMode.MotionMagic, listedSetpoints[currentTarget]);
        
        if(Math.abs(listedSetpoints[currentTarget] - wristMotor.getSelectedSensorPosition()) < kHoldingDeadzone ) {//we're here!
            currentState = WristState.HOLDING;
        }
    }

}