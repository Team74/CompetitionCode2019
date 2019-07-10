package frc.lib.utils;

import frc.lib.utils.geometry.*;
import frc.robot.Constants;
import frc.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

public class SwerveKinematics {
    private RobotMap map = RobotMap.getInstance();
    private AHRS gyro;

    public static enum Speed {
        LOW,
        MID,
        HIGH;
    }

    private Speed speed = Speed.MID;
    private double speedMultiplier = Constants.kMidSpeedPercent;
    private double kStepSize = 0.01;

    private Translation2d frontLeftWheel;
    private Translation2d frontRightWheel;
    private Translation2d rearRightWheel;
    private Translation2d rearLeftWheel;

    private double frontLeftSpeed = 0.0;
    private double frontRightSpeed = 0.0;
    private double rearRightSpeed = 0.0;
    private double rearLeftSpeed = 0.0;
	
    private double frontLeftAngle = 0.0;
    private double frontRightAngle = 0.0;
	private double rearRightAngle = 0.0;
	private double rearLeftAngle = 0.0;

    public Translation2d[] wheels = new Translation2d[4];
    public double[] wheelSpeeds = new double[4];
    public double[] wheelAngles = new double[4];

    public SwerveKinematics() {
        gyro = map.navX;
    }

    public void setSpeed(Speed _speed) {
        if (speed != _speed) {
            speed = _speed;
            switch (speed) {
                case LOW:
                    speedMultiplier = Constants.kLowSpeedPercent;
                case MID:
                    speedMultiplier = Constants.kMidSpeedPercent;
                case HIGH:
                    speedMultiplier = Constants.kHighSpeedPercent;
                default:
                    speedMultiplier = Constants.kMidSpeedPercent;
            }
        }
        return;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void calculate(Translation2d translation, double rotation, boolean isFieldCentric) {
        if (isFieldCentric) {
            double gyroValue = Utilities.boundAngle0to360Degrees(gyro.getAngle());
            translation.rotateBy(Rotation2d.fromDegrees(gyroValue));
        }
        frontLeftWheel = translation.translateBy(Translation2d.fromXYS(Constants.kHalfLength, Constants.kHalfWidth, rotation));
        frontRightWheel = translation.translateBy(Translation2d.fromXYS(Constants.kHalfLength, -(Constants.kHalfWidth), rotation));
        rearRightWheel = translation.translateBy(Translation2d.fromXYS(-(Constants.kHalfLength), -(Constants.kHalfWidth), rotation));
        rearLeftWheel = translation.translateBy(Translation2d.fromXYS(-(Constants.kHalfLength), Constants.kHalfWidth, rotation));

        wheelSpeeds[0] = frontLeftSpeed = frontLeftWheel.norm();
        wheelSpeeds[1] = frontRightSpeed = frontRightWheel.norm();
        wheelSpeeds[2] = rearRightSpeed = rearRightWheel.norm();
        wheelSpeeds[3] = rearLeftSpeed = rearLeftWheel.norm();

        wheelAngles[0] = frontLeftAngle = frontLeftWheel.direction().getDegrees();
        wheelAngles[1] = frontRightAngle = frontRightWheel.direction().getDegrees();
        wheelAngles[2] = rearRightAngle = rearRightWheel.direction().getDegrees();
        wheelAngles[3] = rearLeftAngle = rearLeftWheel.direction().getDegrees();

        double max = frontLeftWheel.norm();
        max = Math.max(frontRightWheel.norm(), max);
        max = Math.max(rearRightWheel.norm(), max);
        max = Math.max(rearLeftWheel.norm(), max);
        //While requesting a maximum speed greater than our limit scale down to the limit   
        while (max * (speedMultiplier * Constants.kRobotMaxVelocity) > Constants.kRobotMaxVelocity) {
            max -= kStepSize;
        }
        frontLeftWheel.scale(max);
        frontRightWheel.scale(max);
        rearRightWheel.scale(max);
        rearLeftWheel.scale(max); 

        wheelSpeeds[0] = frontLeftSpeed = frontLeftWheel.norm();
        wheelSpeeds[1] = frontRightSpeed = frontRightWheel.norm();
        wheelSpeeds[2] = rearRightSpeed = rearRightWheel.norm();
        wheelSpeeds[3] = rearLeftSpeed = rearLeftWheel.norm();
    }

    public Translation2d[] wheels() {
        return wheels;
    }

    public double[] wheelSpeeds() {
        return wheelSpeeds;
    }

    public double[] wheelAngles() {
        return wheelAngles;
    }

    public double frontLeftSpeed() {
        return frontLeftSpeed;
    }

    public double frontRightSpeed() {
        return frontRightSpeed;
    }

    public double rearRightSpeed() {
        return rearRightSpeed;
    }

    public double rearLeftSpeed() {
        return rearLeftSpeed;
    }

    public double frontLeftAngle() {
        return frontLeftAngle;
    }

    public double frontRightAngle() {
        return frontRightAngle;
    }

    public double rearRightAngle() {
        return rearRightAngle;
    }

    public double rearLeftAngle() {
        return rearLeftAngle;
    }
}