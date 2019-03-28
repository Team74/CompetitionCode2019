package frc.robot.drive;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.RobotMap;
import frc.robot.Updateable;

public class Drivetrain implements Updateable {

    public final boolean kMotorInvert = true;
    public final boolean kSensorPhase = false;

    public final double kIZoneS = 0;
    public final double kPS = 15;
    public final double kIS = 0;
    public final double kDS = 0;
    public final double kFS = 4.44;

    public final double kIZoneV = 0;
    public final double kPV = 0.0002;
    public final double kIV = 1e-6;
    public final double kDV = 0;
    public final double kFV = 0;

    //public  spark_1;    //add more motors
    public SwerveModule lf;
    public SwerveModule lb;
    public SwerveModule rf;
    public SwerveModule rb;

    public double mSwerveVectors[][];//{ {lf_a, lf_m}, {rf_a, rf_m}, {lb_a, lb_m}, {rb_a, rf_a} }

    public AHRS gyro;

    private final double WIDTH;
    private final double LENGTH;

    public Drivetrain(RobotMap robotmap) {

        lf = new SwerveModule( robotmap.Drive_0, robotmap.Drive_E_0, robotmap.Steering_0, 64);
        lb = new SwerveModule( robotmap.Drive_1, robotmap.Drive_E_1, robotmap.Steering_1, 245);
        rf = new SwerveModule( robotmap.Drive_2, robotmap.Drive_E_2, robotmap.Steering_2, 417);
        rb = new SwerveModule( robotmap.Drive_3, robotmap.Drive_E_3, robotmap.Steering_3, 297);
        
        lf.drive_motor.setInverted(false);
        
        lb.drive_motor.setInverted(true);
        
        rf.drive_motor.setInverted(false);

        rb.drive_motor.setInverted(false);
        //Set up PIDFs here

        lf.instantiateSteeringPID(kPS, kIS, kDS, kFS, kIZoneS, kSensorPhase, kMotorInvert);
        rf.instantiateSteeringPID(kPS, kIS, kDS, kFS, kIZoneS, kSensorPhase, kMotorInvert);
        lb.instantiateSteeringPID(kPS, kIS, kDS, kFS, kIZoneS, kSensorPhase, kMotorInvert);
        rb.instantiateSteeringPID(kPS, kIS, kDS, kFS, kIZoneS, kSensorPhase, kMotorInvert);  
        
        lf.instantiateVelocityPID(kPV, kIV, kDV, kFV, kIZoneV);
        rf.instantiateVelocityPID(kPV, kIV, kDV, kFV, kIZoneV);
        lb.instantiateVelocityPID(kPV, kIV, kDV, kFV, kIZoneV);
        rb.instantiateVelocityPID(kPV, kIV, kDV, kFV, kIZoneV);

        gyro = robotmap.navX;

        WIDTH = robotmap.wheelBaseWidth;
        LENGTH = robotmap.wheelBaseDepth;

    }

    public void setMove(double speed, double angle, double rotation) {
      //rotation is in rad/sec
      //info for this section from https://www.chiefdelphi.com/t/paper-4-wheel-independent-drive-independent-steering-swerve/107383
        double[][] swerveVectors = new double[4][2];//{ {lf_a, lf_m}, {rf_a, rf_m}, {lb_a, lb_m}, {rb_a, rf_a} }
        
        double vx = speed*Math.sin(angle); //angle is measured from 0 being straight forward, positive turning right, from -pi to pi
        double vy = speed*Math.cos(angle);

        double A = vx - rotation*(LENGTH)/2;
        double B = vx + rotation*(LENGTH)/2;
        double C = vy - rotation*(WIDTH)/2;
        double D = vy + rotation*(WIDTH)/2;
        
        swerveVectors[0][1] = Math.sqrt(Math.pow(B,2) + Math.pow(D,2));//lf 2
        swerveVectors[1][1] = Math.sqrt(Math.pow(B,2) + Math.pow(C,2));//rf 1
        swerveVectors[2][1] = Math.sqrt(Math.pow(A,2) + Math.pow(D,2));//lb 3
        swerveVectors[3][1] = Math.sqrt(Math.pow(A,2) + Math.pow(C,2));//rb 4

        double max = swerveVectors[0][1]; 
        max = swerveVectors[1][1] > max ? swerveVectors[1][1] : max; 
        max = swerveVectors[2][1] > max ? swerveVectors[2][1] : max; 
        max = swerveVectors[3][1] > max ? swerveVectors[3][1] : max;
        //If max is greater than 1, normalize all magnitudes to something less than 1
        if (max > 1){
            swerveVectors[0][1] /= max; 
            swerveVectors[1][1] /= max; 
            swerveVectors[2][1] /= max; 
            swerveVectors[3][1] /= max; 
        }

        swerveVectors[0][0] = Math.atan2(B,D);//lf 2
        swerveVectors[1][0] = Math.atan2(B,C);//rf 1
        swerveVectors[2][0] = Math.atan2(A,D);//lb 3
        swerveVectors[3][0] = Math.atan2(A,C);//rb 4
        manageModules(swerveVectors);
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
        //prob nothing here
    }

}
