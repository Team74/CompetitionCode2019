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

    private double wristAngle = 0.0;//Degrees

    //Is the subsytem running
    private boolean isActive = false;

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

    public void commandWrist(double _wristDemand, WristControlState _WristControlState) {
        switch(_WristControlState) {
            case OPEN_LOOP:
                setOpenLoop(_wristDemand);
                break;
            case POSITION_PID:
                setPositionPID(_wristDemand);
                break;
            case MOTION_MAGIC:
                setMotionMagic(_wristDemand);
                break;
            default:
                break;
        }
    }

    public void setOpenLoop(double percentage) {
        wristControlState = WristControlState.OPEN_LOOP;
        periodicIO.demand = percentage;
    }

    public void setPositionPID(double position) {
        wristControlState = WristControlState.POSITION_PID;
        periodicIO.demand = position;
    }

    public void setMotionMagic(double position) {
        wristControlState = WristControlState.MOTION_MAGIC;
        periodicIO.demand = position;
    }

    /**
     * Method to set our angle calculation to zero.
     * Warning: this does not effect the sesnsors.
     */
    public void zeroAngle() {

    }

    public double getAngle() {
        return wristAngle;
    }

    /**
     * Method to manually set the angle calculation to a value.
     * @param _newAngle Value to set the calculation to.
     */
    public void setAngle(double _newAngle) {
        wristAngle = _newAngle;
    }

    /**
     * Sets the sensor to zero.
     */
    @Override
    public void zeroSensors() {
        wristMotor.setSelectedSensorPosition(0);
    }

    public double getSensorPosition() {
        return wristMotor.getSelectedSensorPosition(0);
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
    }

    @Override
    public boolean isActive() {
        return isActive;
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

    public enum WristControlState {
        OPEN_LOOP,
        POSITION_PID,
        MOTION_MAGIC;
    }

    public static class PeriodicIO {
        //Inputs
        //Control mode to run the motor controller in.
        public ControlMode controlMode;
        //Sensor position reading.
        public int positionTicks;
        //Sensor velocity reading.
        public int velocityTicks;
        //Information from the current Trajectory if using Motion Magic
        public double activeTrajectoryPosition;
        public double activeTrajectoryVelocity;
        public double activeTrajectoryAcceleration;
        //State of any limit switches in the system.
        public boolean limitSwitch;
        //Arbitrary number to add to the output sent to the motors.
        public double arbFeedforward;
        public double t;
        //What to pass to the control setup.
        public double demand;
    }

}