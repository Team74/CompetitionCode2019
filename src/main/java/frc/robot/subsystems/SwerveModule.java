package frc.robot.subsystems;

import frc.utils.Utilities;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class SwerveModule {
    public double kMaxVel = 0.0;

    public double currentAngle;

    public CANSparkMax drive_motor;
    public CANEncoder drive_encoder;
    public WPI_TalonSRX rotate_motor;

    private double targetRotation;
    private double targetSpeed;

    public final int kSlotIdx = 0;
    public final int kPIDLoopIdx = 0;
    public final int kTimeoutMs = 30;

    public final int kEncoderTicks = 1024;

    public int zeroOffset = 0;

    public CANPIDController velController;

    public SwerveModule(CANSparkMax _drive_motor, CANEncoder _drive_encoder, WPI_TalonSRX _rotate_motor, int _zeroOffset) {
        drive_motor = _drive_motor;
        rotate_motor = _rotate_motor;
        drive_encoder = _drive_encoder;
        zeroOffset = _zeroOffset;
        velController = drive_motor.getPIDController();
    }
    
    public void instantiateSteeringPID(double kP, double kI, double kD, double kF, double kIZone, boolean kSensorPhase, boolean kMotorInvert){
        //Start by reseting everything to factory defaults
        rotate_motor.configFactoryDefault(kTimeoutMs);

        rotate_motor.setNeutralMode(NeutralMode.Brake);

        rotate_motor.setInverted(kMotorInvert);

        rotate_motor.configNominalOutputForward(0, kTimeoutMs);
		rotate_motor.configNominalOutputReverse(0, kTimeoutMs);
		rotate_motor.configPeakOutputForward(1, kTimeoutMs);
        rotate_motor.configPeakOutputReverse(-1, kTimeoutMs);

        rotate_motor.configAllowableClosedloopError(0, kPIDLoopIdx, kTimeoutMs);

        rotate_motor.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
		rotate_motor.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
		rotate_motor.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
        rotate_motor.config_kD(kPIDLoopIdx, kD, kTimeoutMs);

        rotate_motor.configMotionCruiseVelocity(230, kTimeoutMs);
        rotate_motor.configMotionAcceleration(150, kTimeoutMs);

        rotate_motor.configSelectedFeedbackSensor(FeedbackDevice.Analog, kPIDLoopIdx, kTimeoutMs);
        rotate_motor.setSensorPhase(kSensorPhase);
        rotate_motor.configFeedbackNotContinuous(false, kTimeoutMs);
    }

    public void instantiateVelocityPID(double kP, double kI, double kD, double kF, double kIZone) {        
        velController.setP(kP, kSlotIdx);
        velController.setI(kI, kSlotIdx);
        velController.setD(kD, kSlotIdx);
        velController.setFF(kF, kSlotIdx);
        velController.setIZone(kIZone, kSlotIdx);
        velController.setOutputRange(-1.0, 1.0, kSlotIdx);

        drive_motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        drive_motor.burnFlash();
    }

    public void setModule(double targetAngle, double _targetSpeed){
        double totalRotation;
        double currentRotation;
        double currentAngle;
        double angleModifier;
        
        //Get current angle
        totalRotation = rotate_motor.getSelectedSensorPosition();
        currentRotation = totalRotation - zeroOffset;// % kEncoderTicks;
        //Change currentRotation to be in radians
        currentAngle = 2*(Math.PI)*(currentRotation/kEncoderTicks);
        currentAngle %= 2*Math.PI;

        if(Math.abs(currentAngle) > Math.PI) {
            currentAngle -= Math.signum(currentAngle)*Math.PI*2;
        }        
        //Change target angle to be on the 0 to 2pi range
        targetAngle = targetAngle < 0 ? targetAngle + 2*(Math.PI) : targetAngle;

        currentAngle = Math.toDegrees(currentAngle);
        targetAngle = Math.toDegrees(targetAngle);

        double adjustedCurrentAngle = currentAngle < 0 ? 180 + currentAngle : currentAngle;
        double adjustedTargetAngle = targetAngle < 0 ? 180 + targetAngle : targetAngle;

        double adjustedDelta = (adjustedTargetAngle - adjustedCurrentAngle);
        adjustedDelta = Math.abs(adjustedDelta) > 90 ? -Math.signum(adjustedDelta)*(180 - Math.abs(adjustedDelta)) : adjustedDelta;
        
        //Convert angle modifier to encoder
        angleModifier = Utilities.angleToEncoder(adjustedDelta, kEncoderTicks);
        
        //Add modifier to current total rotation to get new referance point
        targetRotation = currentRotation + angleModifier;

        targetSpeed = _targetSpeed;
        double speedDifference = (targetAngle - currentAngle);
        speedDifference += (speedDifference > 180) ? -360 : ( (speedDifference < -180) ? 360 : 0 );
        if(Math.abs(speedDifference) > 90) {
            targetSpeed *= -1;
        }
        setMotors(targetRotation, targetSpeed);
    }

    public void setMotors(double _targetRotation, double _targetSpeed) {
        if (_targetSpeed == 0) {
            drive_motor.stopMotor();
            rotate_motor.stopMotor();
        } else {
            double targetSpeed = _targetSpeed * kMaxVel;
            if (targetSpeed != 0){
                velController.setReference(targetSpeed, ControlType.kVelocity);
            } else{
                drive_motor.stopMotor();
            }
            rotate_motor.set(ControlMode.MotionMagic, (_targetRotation + zeroOffset));
        }
    }

    public void setMotorsPercentOutput(double _angularVelocity, double _targetSpeed) {
        rotate_motor.set(_angularVelocity);
        drive_motor.set(_targetSpeed);
    }

    public void setCurrentAngle(){  //called from outside purely (in theory) so that currentAngle can be read for the Dashboard
        double currentEncoder = (rotate_motor.getSelectedSensorPosition() + zeroOffset) % kEncoderTicks;
        currentAngle = Utilities.encoderToAngle(currentEncoder, kEncoderTicks);
    }
}
