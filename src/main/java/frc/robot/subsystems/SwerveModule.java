package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class SwerveModule {
    public final double maxVel = 0.0;

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

    CANPIDController velController = new CANPIDController(drive_motor);

    public SwerveModule(CANSparkMax _drive_motor, CANEncoder _drive_encoder, WPI_TalonSRX _rotate_motor, int _zeroOffset) {
        drive_motor = _drive_motor;
        rotate_motor = _rotate_motor;
        drive_encoder = _drive_encoder;
        zeroOffset = _zeroOffset;

    }
    
    public void instantiateSteeringPID(double kP, double kI, double kD, double kF, double kIZone, boolean kSensorPhase, boolean kMotorInvert){
        //HandleEncoder encoder = new HandleEncoder(rotate_motor, kSensorPhase);
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
    /*
    public void instantiateVelocityPID(double kP, double kI, double kD, double kF, double kIZone) {
        //We're going to tune the gains using the tuner than hardcode them.
        /*
        velController.setP(kP, kSlotIdx);
        velController.setI(kI, kSlotIdx);
        velController.setD(kD, kSlotIdx);
        velController.setFF(kF, kSlotIdx);
        velController.setIZone(kIZone, kSlotIdx);
        velController.setOutputRange(-1.0, 1.0, kSlotIdx);
        drive_motor.burnFlash();
        /*


    }
    */

    public void stopMotors() {
        rotate_motor.stopMotor();
        drive_motor.stopMotor();
    }

    public void setModule(double targetAngle, double _targetSpeed){
        double totalRotation;
        double currentRotation;
        double currentAngle;
        double angleDelta;
        double angleModifier;
        double absAngleDelta;

        double targetEncoderTicks = angleToEncoder(Math.toDegrees(targetAngle));

    // System.out.println("Raw target Angle: " + Math.toDegrees(targetAngle));

        //Get current angle
        totalRotation = rotate_motor.getSelectedSensorPosition();
        //System.out.println("Encoder: " + totalRotation);
        currentRotation = totalRotation - zeroOffset;// % kEncoderTicks;
        //Change currentRotation to be in radians
        currentAngle = 2*(Math.PI)*(currentRotation/kEncoderTicks);
        //currentAngle += Math.PI;
        currentAngle %= 2*Math.PI;
        //System.out.println(Math.toDegrees(currentAngle));
        //currentAngle = currentAngle < 0 ? currentAngle + 2*Math.PI : currentAngle;
        if(Math.abs(currentAngle) > Math.PI) {
            currentAngle -= Math.signum(currentAngle)*Math.PI*2;
        }        
        //Change target angle to be on the 0 to 2pi range
        targetAngle = targetAngle < 0 ? targetAngle + 2*(Math.PI) : targetAngle;

        //more attempts to fix here

        currentAngle = Math.toDegrees(currentAngle);
        targetAngle = Math.toDegrees(targetAngle);

        double adjustedCurrentAngle = currentAngle < 0 ? 180 + currentAngle : currentAngle;
        double adjustedTargetAngle = targetAngle < 0 ? 180 + targetAngle : targetAngle;

        double adjustedDelta = (adjustedTargetAngle - adjustedCurrentAngle);
        adjustedDelta = Math.abs(adjustedDelta) > 90 ? -Math.signum(adjustedDelta)*(180 - Math.abs(adjustedDelta)) : adjustedDelta;
        
        //Convert angle modifier to encoder
        angleModifier = angleToEncoder(adjustedDelta);
        
        //Add modifier to current total rotation to get new referance point
        targetRotation = currentRotation + angleModifier;

        targetSpeed = _targetSpeed;
        double speedDifference = (targetAngle - currentAngle);
        speedDifference += (speedDifference > 180) ? -360 : ( (speedDifference < -180) ? 360 : 0 );
        if(Math.abs(speedDifference) > 90) {
            targetSpeed *= -1;
        }


        
        setMotors(targetRotation, targetSpeed * 0.4);
        
//more attempts to fix here

        //Calculate the differance in angle
        /*angleDelta = Math.toDegrees(targetAngle - currentAngle);  //convert to degrees


        angleDelta = Math.abs(angleDelta) > 180 ? angleDelta - Math.signum(angleDelta)*180 : angleDelta;

        absAngleDelta = Math.abs(angleDelta);
        //Begin checking cases
        targetSpeed = _targetSpeed;

        if(absAngleDelta <= 90) {
            angleModifier = angleDelta;
        } else {
            angleModifier = angleDelta - 180 * Math.signum(angleDelta);
            targetSpeed *= -1;
        }
//*/
    /*    System.out.println("Angle Delta: " + adjustedDelta);// angleDelta);//(angleDelta/Math.PI*180));
        System.out.println("Current Angle: " + adjustedCurrentAngle); //currentAngle/Math.PI*180);
        System.out.println("Target Angle: " + adjustedTargetAngle);// targetAngle/Math.PI*180);
        System.out.println("Target Speed: " + targetSpeed);
        System.out.println("-");
//*/

/*
        if (absAngleDelta <= 90) {
            angleModifier = angleDelta;
        } /*else if (90 < absAngleDelta && absAngleDelta <= 180) {
            angleModifier = angleDelta - 180;//or -(180-delta)
            //Reverse wheel direction
            targetSpeed = _targetSpeed * -1;
        }else if (180 < absAngleDelta && absAngleDelta < 270) {
            angleModifier = angleDelta - 180;
            //Reverse wheel direction
            targetSpeed = _targetSpeed * -1;
        }else if (270 <= absAngleDelta && absAngleDelta < 360) {
            angleModifier = Math.abs(absAngleDelta - 360);// or -(360-delta)
        *///  else {
         //   angleModifier = 0;
        //}//*/
        //Convert angle modifier to encoder
        //angleModifier = angleToEncoder(angleModifier);
        
        //Add modifier to current total rotation to get new referance point
        //targetRotation = currentRotation + angleModifier;
        
        //setMotors(targetRotation, 0*targetSpeed / 10);
        
        //System.out.println("Current Rotation: " + currentRotation);
    }

    public void setMotors(double _targetRotation, double _targetSpeed) {
        drive_motor.set(_targetSpeed);
        rotate_motor.set(ControlMode.MotionMagic, (_targetRotation + zeroOffset));
    }

    public void setMotorsPercentOutput(double _angularVelocity, double _targetSpeed) {
        rotate_motor.set(_angularVelocity);
        drive_motor.set(_targetSpeed);
    }

    public double angleToEncoder(double angle){
        double encoder;
        encoder = (angle/360) * kEncoderTicks;
        return encoder;
    }
    }
