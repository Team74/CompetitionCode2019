package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Constants;

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

    private final CANSparkMax elevatorMotor;
    private final CANEncoder elevatorEncoder;
    private final CANPIDController elevatorController;
    private final int kMotionMagicSlot = 0;
    private final int kPositionPIDSlot = 1;
    private final int kDutyCycleSlot = 2;
    private static final double kEncoderTicksPerInch = 0.0;   
    private PeriodicIO periodicIO = new PeriodicIO();
    private ElevatorControlState elevatorControlState = ElevatorControlState.OPEN_LOOP;

    public static Elevator getInstance() {
        if (kInstance == null) {
            kInstance = new Elevator();
        }
        return kInstance;
    }

    private Elevator() {
        elevatorMotor = RobotMap.getInstance().Elevator_0;
        elevatorEncoder = RobotMap.getInstance().Elevator_E_0;
        elevatorController = elevatorMotor.getPIDController();

        elevatorMotor.clearFaults();
        elevatorMotor.setIdleMode(IdleMode.kBrake);

        elevatorController.setP(Constants.kElevatorP, kMotionMagicSlot);
        elevatorController.setI(Constants.kElevatorI, kMotionMagicSlot);
        elevatorController.setD(Constants.kElevatorD, kMotionMagicSlot);
        elevatorController.setFF(Constants.kElevatorF, kMotionMagicSlot);

        elevatorController.setSmartMotionMaxVelocity(Constants.kElevatorMaxVelocity, kMotionMagicSlot);
        elevatorController.setSmartMotionMinOutputVelocity(0, kMotionMagicSlot);
        elevatorController.setSmartMotionMaxAccel(Constants.kElevatorMaxAcceleration, kMotionMagicSlot);
        elevatorController.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, kMotionMagicSlot);
        elevatorController.setOutputRange(-1, 1, kMotionMagicSlot);//Check to make sure that this is the correct implementation
    }

    public void setOpenloop(double percentage) {
        elevatorControlState = ElevatorControlState.OPEN_LOOP;
        periodicIO.demand = percentage;
    }

    public void setPositionPID(double desiredPosition) {
        elevatorControlState = ElevatorControlState.POSITION_PID;
        periodicIO.demand = desiredPosition;
    }

    public void setMotionMagicPosition(double desiredPosition) {
        elevatorControlState = ElevatorControlState.MOTION_MAGIC;
        periodicIO.demand = desiredPosition;
    }

    @Override
    public void zeroSensors() {
        elevatorEncoder.setPosition(0);
    }

    @Override
    public void stop() {
        setOpenloop(0.0);
    }

    @Override
    public void readPeriodicInputs() {
        periodicIO.t = Timer.getFPGATimestamp();
        periodicIO.positionTicks = elevatorEncoder.getPosition();
        periodicIO.velocityTicks = elevatorEncoder.getVelocity();
        //Add limit switch gets
        periodicIO.arbFeedforward = Constants.kElevatorFeedfoward;//If we need to, this can change if we, for example, are carrying a ball.
    }

    @Override
    public void writePeriodicOutputs() {
        switch(elevatorControlState) {
            case OPEN_LOOP:
                elevatorController.setReference(periodicIO.demand, ControlType.kDutyCycle, kDutyCycleSlot, periodicIO.arbFeedforward);
                break;
            case POSITION_PID:
                elevatorController.setReference(periodicIO.demand, ControlType.kPosition, kPositionPIDSlot, periodicIO.arbFeedforward);
                break;
            case MOTION_MAGIC:
                elevatorController.setReference(periodicIO.demand, ControlType.kSmartMotion, kMotionMagicSlot, periodicIO.arbFeedforward);
                break;
            default:
                elevatorController.setReference(periodicIO.demand, ControlType.kDutyCycle, kDutyCycleSlot, periodicIO.arbFeedforward);
                break;
        }
    }

    @Override
    public void outputTelemetry() {

    }

    private enum ElevatorControlState{
        OPEN_LOOP,
        POSITION_PID,
        MOTION_MAGIC;
    }

    public static class PeriodicIO {
        //Inputs
        public ControlType controlMode;
        public double positionTicks;
        public double velocityTicks;//Figure out time units
        /* These arrn't gettable on a SparkMax
        public double activeTrajectoryPosition;
        public double activeTrajectoryVelocity;
        public double activeTrajectoryAcceleration;
        */
        public boolean bottonLimit;
        public boolean topLimit;
        public double arbFeedforward;
        public double t;

        //Outputs
        public double demand;
    }
}