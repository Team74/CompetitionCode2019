package frc.robot.drive;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.Updateable;

import frc.lib.utils.geometry.Translation2d;
import frc.lib.utils.SwerveKinematics;

public class Drivetrain implements Updateable {
    public static Drivetrain kInstance = null;

    public SwerveKinematics SwerveKinematics = new SwerveKinematics();

    //For teleop swerve control
    private final int kLowSpeed = 1000;
    private final int kMidSpeed = 2500;
    private final int kHighSpeed = 5000;
    public double speed = 0.0;
    public double angle = 0.0;
    public double rotation = 0.0;

    public static enum Speed{
        Low, Mid, High;
    }
    
    public final boolean kSteeringMotorInvert = true;
    public final boolean kSteeringSensorPhase = false;
    public final double kSteeringIZone = 0;
    public final double kDriveIZone = 0;

    public SwerveModule lf;
    public SwerveModule lb;
    public SwerveModule rf;
    public SwerveModule rb;

    public double mSwerveVectors[][];//{ {lf_a, lf_m}, {rf_a, rf_m}, {lb_a, lb_m}, {rb_a, rf_a} }

    public AHRS gyro;

    public static Drivetrain getInstance() {
        if (kInstance == null) {
            kInstance = new Drivetrain(RobotMap.getInstance());
        }
        return kInstance;
    }

    private Drivetrain(RobotMap robotmap) {

        lf = new SwerveModule(robotmap.Drive_0, robotmap.Drive_E_0, robotmap.Steering_0, 64);
        lb = new SwerveModule(robotmap.Drive_1, robotmap.Drive_E_1, robotmap.Steering_1, 245);
        rf = new SwerveModule(robotmap.Drive_2, robotmap.Drive_E_2, robotmap.Steering_2, 417);
        rb = new SwerveModule(robotmap.Drive_3, robotmap.Drive_E_3, robotmap.Steering_3, 297);
        
        lf.drive_motor.setInverted(false);
        
        lb.drive_motor.setInverted(true);
        
        rf.drive_motor.setInverted(false);

        rb.drive_motor.setInverted(false);
        //Set up PIDFs here

        lf.instantiateSteeringPID(Constants.kSteeringP, Constants.kSteeringI, Constants.kSteeringD, Constants.kSteeringF, kSteeringIZone, kSteeringSensorPhase, kSteeringMotorInvert);
        rf.instantiateSteeringPID(Constants.kSteeringP, Constants.kSteeringI, Constants.kSteeringD, Constants.kSteeringF, kSteeringIZone, kSteeringSensorPhase, kSteeringMotorInvert);
        lb.instantiateSteeringPID(Constants.kSteeringP, Constants.kSteeringI, Constants.kSteeringD, Constants.kSteeringF, kSteeringIZone, kSteeringSensorPhase, kSteeringMotorInvert);
        rb.instantiateSteeringPID(Constants.kSteeringP, Constants.kSteeringI, Constants.kSteeringD, Constants.kSteeringF, kSteeringIZone, kSteeringSensorPhase, kSteeringMotorInvert);  
        
        lf.instantiateVelocityPID(Constants.kDriveP, Constants.kDriveI, Constants.kDriveD, Constants.kDriveF, kDriveIZone);
        rf.instantiateVelocityPID(Constants.kDriveP, Constants.kDriveI, Constants.kDriveD, Constants.kDriveF, kDriveIZone);
        lb.instantiateVelocityPID(Constants.kDriveP, Constants.kDriveI, Constants.kDriveD, Constants.kDriveF, kDriveIZone);
        rb.instantiateVelocityPID(Constants.kDriveP, Constants.kDriveI, Constants.kDriveD, Constants.kDriveF, kDriveIZone);

    }

    public void setSpeed(Speed _speed){
        switch(_speed){
            case Low:
                //System.out.println("Set Speed: Low");
                lf.kMaxVel = kLowSpeed;
                lb.kMaxVel = kLowSpeed;
                rf.kMaxVel = kLowSpeed;
                rb.kMaxVel = kLowSpeed;
                break;
            case Mid:
                //System.out.println("Set Speed: Mid");
                lf.kMaxVel = kMidSpeed;
                lb.kMaxVel = kMidSpeed;
                rf.kMaxVel = kMidSpeed;
                rb.kMaxVel = kMidSpeed;
                break;
            case High:
                //System.out.println("Set Speed: High");
                lf.kMaxVel = kHighSpeed;
                lb.kMaxVel = kHighSpeed;
                rf.kMaxVel = kHighSpeed;
                rb.kMaxVel = kHighSpeed;
                break;
            default:
                //System.out.println("Set Speed: Default");
                lf.kMaxVel = kMidSpeed;
                lb.kMaxVel = kMidSpeed;
                rf.kMaxVel = kMidSpeed;
                rb.kMaxVel = kMidSpeed;
                break;
        }
    }

    public void swerve(Translation2d _translation, double _rotation) {

    }

    public void manageModules(double swerveVectors[][]){
        mSwerveVectors = swerveVectors;
        //Set module args are targetAngle, targetSpeed

        lf.setModule(mSwerveVectors[0][0], mSwerveVectors[0][1]);
        rf.setModule(mSwerveVectors[1][0], mSwerveVectors[1][1]);
        lb.setModule(mSwerveVectors[2][0], mSwerveVectors[2][1]);
        rb.setModule(mSwerveVectors[3][0], mSwerveVectors[3][1]);

        lf.setCurrentAngle();
        lb.setCurrentAngle();
        rf.setCurrentAngle();
        rb.setCurrentAngle();
    }

    public void update(double dt) {

    }

}
