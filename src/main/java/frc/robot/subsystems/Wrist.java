package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Wrist implements Subsystem {
    public static Wrist kInstance = null;

    private final TalonSRX wristMotor = RobotMap.getInstance().Wrist_0;
    private PeriodicIO periodicIO = new PeriodicIO();
    private WristControlState wristControlState = WristControlState.OPEN_LOOP;

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

    public void setOpenLoop(double percentage) {
        wristControlState = WristControlState.OPEN_LOOP;
        periodicIO.demand = percentage;
    }

    public void setPositionPIDPosition(double position) {
        wristControlState = WristControlState.POSITION_PID;
        periodicIO.demand = position;
    }

    public void setMotionMagicPosition(double position) {
        wristControlState = WristControlState.MOTION_MAGIC;
        periodicIO.demand = position;
    }

    @Override
    public void zeroSensors() {
        wristMotor.setSelectedSensorPosition(0);
    }

    @Override
    public void stop() {

    }

    @Override
    public void readPeriodicInputs() {

    }

    @Override
    public void writePeriodicOutputs() {

    }

    @Override
    public void outputTelemetry() {

    }

    private enum WristControlState {
        OPEN_LOOP,
        POSITION_PID,
        MOTION_MAGIC;
    }

    public static class PeriodicIO {
        //Inputs
        public ControlMode controlMode;
        public int positionTicks;
        public int velocityTicks;
        public double activeTrajectoryPosition;
        public double activeTrajectoryVelocity;
        public double activeTrajectoryAcceleration;
        public boolean limitSwitch;
        public double arbFeedforward;
        public double t;


        //Outputs
        public double demand;
    }

}