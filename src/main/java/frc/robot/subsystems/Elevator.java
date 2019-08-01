package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants;

import frc.lib.motorcontroller.WrappedSparkMax;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;

public class Elevator implements Subsystem {
    public static Elevator kInstance = null;

    private final WrappedSparkMax elevatorMotor;
    private final int kMotionMagicSlot = 0;
    private final int kPositionPIDSlot = 1;
    private final int kDutyCycleSlot = 2;
    private PeriodicIO periodicIO = new PeriodicIO();
    private ElevatorControlState elevatorControlState = ElevatorControlState.OPEN_LOOP;

    private final double kEncoderTicksPerInch = 0.0;
    private double elevatorHeight = 0.0;//Inches
    
    //Is the subsytem running
    private boolean isActive = false;

    public static Elevator getInstance() {
        if (kInstance == null) {
            kInstance = new Elevator();
        }
        return kInstance;
    }

    private Elevator() {
        elevatorMotor = RobotMap.getInstance().Elevator_0;

        elevatorMotor.clearFaults();
        elevatorMotor.setIdleMode(IdleMode.kBrake);

        elevatorMotor.setP(Constants.kElevatorP, kMotionMagicSlot);
        elevatorMotor.setI(Constants.kElevatorI, kMotionMagicSlot);
        elevatorMotor.setD(Constants.kElevatorD, kMotionMagicSlot);
        elevatorMotor.setF(Constants.kElevatorF, kMotionMagicSlot);

        //Check to make sure that the output range, (-1, 1) values are implemented correctly.
        elevatorMotor.configSmartMotion(Constants.kElevatorMaxVelocity, 0, Constants.kElevatorMaxAcceleration, AccelStrategy.kTrapezoidal, -1, 1, kMotionMagicSlot);
    }

    public void commandElevator(double _elevatorDemand, ElevatorControlState _elevatorControlState) {
        switch(_elevatorControlState) {
            case OPEN_LOOP:
                setOpenloop(_elevatorDemand);
                break;
            case POSITION_PID:
                setPositionPID(_elevatorDemand);
                break;
            case MOTION_MAGIC:
                setMotionMagic(_elevatorDemand);
                break;
            default:
                break;
        }
    }

    public void setOpenloop(double percentage) {
        elevatorControlState = ElevatorControlState.OPEN_LOOP;
        periodicIO.demand = percentage;
    }

    public void setPositionPID(double desiredPosition) {
        elevatorControlState = ElevatorControlState.POSITION_PID;
        periodicIO.demand = desiredPosition;
    }

    public void setMotionMagic(double desiredPosition) {
        elevatorControlState = ElevatorControlState.MOTION_MAGIC;
        periodicIO.demand = desiredPosition;
    }

    /**
     * Method to set our hieght calculation to zero.
     * Warning: Does not set sensor to 0.
     */
    public void zeroHeight() {
        elevatorHeight = 0.0;
    }

    public double getHeight() {
        return elevatorHeight;
    }

    /**
     * Method to set the sensors to zero.
     */
    @Override
    public void zeroSensors() {
        elevatorMotor.setPosition(0);
    }

    public double getSensorPosition() {
        return elevatorMotor.getPosition();
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
        periodicIO.t = Timer.getFPGATimestamp();
        periodicIO.positionTicks = elevatorMotor.getPosition();
        periodicIO.velocityTicks = elevatorMotor.getVelocity();
        //Add limit switch gets
        periodicIO.arbFeedforward = Constants.kElevatorFeedfoward;//If we need to, this can change if we, for example, are carrying a ball.
    }

    @Override
    public void writePeriodicOutputs() {
        switch(elevatorControlState) {
            case OPEN_LOOP:
                elevatorMotor.setReference(periodicIO.demand, ControlType.kDutyCycle, kDutyCycleSlot, periodicIO.arbFeedforward);
                break;
            case POSITION_PID:
                elevatorMotor.setReference(periodicIO.demand, ControlType.kPosition, kPositionPIDSlot, periodicIO.arbFeedforward);
                break;
            case MOTION_MAGIC:
                elevatorMotor.setReference(periodicIO.demand, ControlType.kSmartMotion, kMotionMagicSlot, periodicIO.arbFeedforward);
                break;
            default:
                elevatorMotor.setReference(periodicIO.demand, ControlType.kDutyCycle, kDutyCycleSlot, periodicIO.arbFeedforward);
                break;
        }
    }

    @Override
    public void outputTelemetry() {

    }

    public enum ElevatorControlState{
        OPEN_LOOP,
        POSITION_PID,
        MOTION_MAGIC;
    }

    public static class PeriodicIO {
        //Inputs
        //Control mode to run the motor controller in.
        public ControlType controlMode;
        //Sensor position reading.
        public double positionTicks;
        //Sensor velocity reading.
        public double velocityTicks;//Figure out time units for SparkMax
        //Information from the current Trajectory if using Motion Magic
        /* These arrn't gettable on a SparkMax
        public double activeTrajectoryPosition;
        public double activeTrajectoryVelocity;
        public double activeTrajectoryAcceleration;
        */
        //State of any limit switches in the system.
        public boolean limitSwitch;
        //Arbitrary number to add to the output sent to the motors.
        public double arbFeedforward;
        public double t;
        //What to pass to the control setup.
        public double demand;
    }
}