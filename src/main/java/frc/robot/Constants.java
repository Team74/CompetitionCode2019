package frc.robot;

import frc.lib.utils.geometry.Translation2d;

public class Constants {
    public static final double dt = 0.02;
    public static final double kJoystickDeadband = 0.05;

    //Physical robot dimensions
    public static final double kRobotWidth = 0.0;//Side to side
    public static final double kRobotLength = 0.0;//Front to back
    public static final double kRobotHalfWidth = kRobotWidth / 2.0;
    public static final double kRobotHalfLength = kRobotLength / 2.0;
    public static final double kIntakeLength = 0.0;

    //Swerve Dimensions
    public static final double kWheelBaseWidth = 24.375;
    public static final double kWheelBaseLength = 22.375;
    public static final double kHalfWidth = kWheelBaseWidth / 2;
    public static final double kHalfLength = kWheelBaseLength / 2;
    public static final double kSwerveDiagonal = Math.hypot(kWheelBaseWidth, kWheelBaseLength);

    //About turning center which is at (0,0)
    public static final Translation2d kVehicleToFrontLeftSwerve = new Translation2d(-kHalfWidth, kHalfLength);
    public static final Translation2d kVehicleToFrontRightSwerve = new Translation2d(kHalfWidth, kHalfLength);
    public static final Translation2d kVehicleToRearRightSwerve = new Translation2d(kHalfWidth, -kHalfLength);
    public static final Translation2d kVehicleToRearLeftSwerve = new Translation2d(-kHalfWidth, -kHalfLength);


    //Swerve Constraints
    public static final double kRobotMaxVelocity = 0.0;
    public static final double kRobotMaxAcceleration = 0.0;
    public static final double kRobotMaxJerk = 0.0;
    public static final int kRobotSteeringMaxVelocity = 0;
    public static final int kRobotSteeringMaxAcceleration = 0;
    public static final int kRobotSteeringMaxJerk = 0;

    public static final double kLowSpeedPercent = 0.0;
    public static final double kMidSpeedPercent = 0.0;
    public static final double kHighSpeedPercent = 0.0;

    //Swerve PIDF
    public static final double kDriveP = 0.0002;
    public static final double kDriveI = 1e-6;
    public static final double kDriveD = 0.0;
    public static final double kDriveF = 0.0;
    public static final double kSteeringP = 15;
    public static final double kSteeringI = 0.0;
    public static final double kSteeringD = 0.0;
    public static final double kSteeringF = 4.44;

    //Elevator Positions
    public static final double kElevatorStartingPosition = 0.0;
    public static final double kELevatorUnhookPosition = 0.0;
    public static final double kStowElevator = 0.0;

    public static final double kIntakeBall = 0.0;
    public static final double kCarryBall = 0.0;
    public static final double kLowBall = 0.0;
    public static final double kMidBall = 0.0;
    public static final double kHighBall = 0.0;
    public static final double kCargoBall = 0.0;

    public static final double kIntakePanel = 0.0;
    public static final double kCarryPanel = 0.0;
    public static final double kLowPanel = 0.0;
    public static final double kMidPanel = 0.0;
    public static final double kHighPanel = 0.0;

    public static final double kStartHighClimb = 0.0;
    public static final double kStartLowClimb = 0.0;
    public static final double kEndClimb = 0.0;

    //Elevator Constraints
    public static final double kElevatorMaxVelocity = 0.0;//RPM
    public static final double kElevatorMaxAcceleration = 0.0;//RPM^2
    public static final double kELevatorMaxJerk = 0.0;//RPM^3
    public static final double kElevatorMaxVolts = 0.0;//Volts
    public static final double kElevatorMaxCurrent = 0.0;//Amps
    public static final double kElevatorCurrentLimit = 0.0;//Amps

    //Elevator PIDF
    public static final double kElevatorP = 0.0;
    public static final double kElevatorI = 0.0;
    public static final double kElevatorD = 0.0;
    public static final double kElevatorF = 1/kElevatorMaxVelocity;
    public static final double kElevatorFeedfoward = 0.0;

    //Wrist Positions
    public static final int kStartingPosition = 0;
    public static final int kStowWrist = 0;
    public static final int kTravelWrist = 0;

    public static final int kWristClimbPosition = 0;

    public static final int kPanelIntake = 0;
    public static final int kPanelPlace = 0;
    public static final int kPanelCarry = 0;
    
    public static final int kBallIntake = 0;
    public static final int kScoreBallLow = 0;
    public static final int kScoreBallMid = 0;
    public static final int kScoreBallHigh = 0;
    public static final int kScoreBallCargo = 0;

    //Wrist Constraints
    public static final int kWristMaxVelocity = 0;
    public static final int kWristMaxAcceleration = 0;
    public static final int kWristMaxJerk = 0;
    public static final double kWristMaxVolts = 0.0;
    public static final double kWristMaxCurrent = 0.0;
    public static final double kWristCurrentLimit = 0.0;

    //Wrist PIDF
    public static final double kWristP = 0.0;
    public static final double kWristD = 0.0;
    public static final double kWristI = 0.0;
    public static final double kWristF = 1023 / kWristMaxVelocity;

    //Climber Positions
    public static final double kClimberStartingPosition = 0.0;
    public static final double kLowClimb = 0.0;
    public static final double kHighClimb = 0.0;
    public static final double kStowClimb = 0.0;

    //Climber Constraints
    public static final double kClimberMaxVelocity = 0.0;
    public static final double kClimberMaxAcceleration = 0.0;
    public static final double kClimberMaxJerk = 0.0;
    public static final double kClimberMaxVolts = 0.0;
    public static final double kClimberMaxCurrent = 0.0;
    public static final double kClimberCurrentLimit = 0.0;

    //Climber PIDF
    public static final double kClimberP = 0.0;
    public static final double kClimberI = 0.0;
    public static final double kClimberD = 0.0;
    public static final double kClimberF = 1023 / kClimberMaxVelocity;
}