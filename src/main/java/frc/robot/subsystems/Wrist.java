package frc.robot.subsystems;

import frc.robot.SubsystemManager;
import frc.robot.Updateable;
import frc.robot.RobotMap;

import static frc.robot.RobotMap.isWristUp;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import java.util.HashMap;

public class Wrist implements Updateable {
    // Parallel Setpoint 0, Perpendiular Setpoint 3190, Cargo Diagonal setpoint 2000
    //Parallel maybe different from Stow

    public SubsystemManager mSubsystemManager;
    public RobotMap mRobotMap;

    public TalonSRX wristMotor;

    private final int kSlotIDX = 0;
    private final int kTimeoutMs = 30;
    
    public final double kCurrentLimit = 40.0;
    public boolean currentFlag = false;
    public boolean limitSwitchFlag = false;

    public final boolean kMotorInvert = true;
    public final boolean kSensorPhase = true;

    public double kP, kI, kD, kF;

    public final int kMaxVel = 400;
    public final int kMaxAccel = 300;

    public final double kHoldingDeadzone = 0.0;

    public HashMap<String, Integer> kWristSetPoints = new HashMap<String, Integer>();

    public int currentTarget;

    public static enum WristState {
        HOLDING, MOVING, MANUAL;
    }
    public WristState currentState;

    private boolean isManual = false;

    public Wrist(SubsystemManager _subsystemManager, RobotMap _robotMap){
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;
        wristMotor = mRobotMap.Wrist_0;

        wristMotor.configFactoryDefault(kTimeoutMs);
        
        wristMotor.setNeutralMode(NeutralMode.Brake);

        wristMotor.setInverted(kMotorInvert);

        wristMotor.configNominalOutputForward(0, kTimeoutMs);
		wristMotor.configNominalOutputReverse(0, kTimeoutMs);
		wristMotor.configPeakOutputForward(1, kTimeoutMs);
        wristMotor.configPeakOutputReverse(-1, kTimeoutMs);

        wristMotor.configAllowableClosedloopError(0, kSlotIDX, kTimeoutMs);

        wristMotor.configMotionCruiseVelocity(kMaxVel, kTimeoutMs);
        wristMotor.configMotionAcceleration(kMaxAccel, kTimeoutMs);

        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kSlotIDX, kTimeoutMs);//Not sure what feeback device type to use
        wristMotor.setSensorPhase(kSensorPhase);
        wristMotor.setSelectedSensorPosition(0, kSlotIDX, kTimeoutMs);

        kP = 45;
        kI = 0.0;
        kD = 5000;
        kF = .8525;

        wristMotor.config_kF(kSlotIDX, kF, kTimeoutMs);
		wristMotor.config_kP(kSlotIDX, kP, kTimeoutMs);
		wristMotor.config_kI(kSlotIDX, kI, kTimeoutMs);
        wristMotor.config_kD(kSlotIDX, kD, kTimeoutMs);

        setSetpoints();
    }

    public void updatePIDFCoefficents() {
        //Check dashboard to see if coefficents have changed
        double _p = mSubsystemManager.mDashboard.wristP.getDouble(kP);
        double _i = mSubsystemManager.mDashboard.wristI.getDouble(kI);
        double _d = mSubsystemManager.mDashboard.wristD.getDouble(kD);
        double _f = mSubsystemManager.mDashboard.wristF.getDouble(kF);

        if (kP != _p) {
            kP = _p;
            wristMotor.config_kP(kSlotIDX, kP, kTimeoutMs);
        } 
        if (kI != _i) {
            kI = _i;
            wristMotor.config_kI(kSlotIDX, kI, kTimeoutMs);
        }
        if (kD != _d) {
            kD = _d;
            wristMotor.config_kD(kSlotIDX, kD, kTimeoutMs);
        }
        if (kF != _f) {
            kF = _f;
            wristMotor.config_kF(kSlotIDX, kF, kTimeoutMs);
        }
    }

    public void checkLimit() {
        if (isWristUp.get()) {
            wristMotor.setSelectedSensorPosition(0, kSlotIDX, kTimeoutMs);
            limitSwitchFlag = true;
        }
    }

    public void setSetpoints() {
        kWristSetPoints.put("Stow", 0);
        kWristSetPoints.put("Perpendicular", 2750);
        kWristSetPoints.put("Parallel", 300);
        kWristSetPoints.put("CargoDiagonal",700);
    }

    public void setTarget(String _target) {
        currentTarget = kWristSetPoints.get(_target);
        System.out.println("Wrist Target Set: " + _target);
    }

    public void setIsManual(boolean _temp) {
        isManual = _temp;
    }

    public boolean getIsManual() {
        return isManual;
    }

    public void updateState() {
        if (Math.abs(currentTarget - wristMotor.getSelectedSensorPosition()) <= kHoldingDeadzone ) {
            //At target
            currentState = WristState.HOLDING;
            //System.out.println("Wrist State Set: " + WristState.HOLDING); 
        } else {
            currentState= WristState.MOVING;
        }
    }
    /*
    public void set(ControlMode _controlMode, double _demand) {
        if (kCurrentLimit <= Math.abs(wristMotor.getOutputCurrent())) {
            currentFlag = true;
        }
        if (currentFlag) {
            wristMotor.set(ControlMode.PercentOutput, 0.0);
        } else {
            wristMotor.set(_controlMode, _demand);
        }
    }
    */
    public void update(double dT) {
        updateState();
                
        if (!getIsManual()) {
            wristMotor.set(ControlMode.MotionMagic, currentTarget);
        } else {

        }
        
    }

}