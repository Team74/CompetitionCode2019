package frc.robot;

import com.revrobotics.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
/*
This class sets up all the different things related to the PWM, plus some other stuff. It'll get passed in to other classes so they can get references back to the individual components.
*/
public class RobotMap {
    //These are set in inches
    public final double wheelBaseWidth = 24.375;
    public final double wheelBaseDepth = 22.375;

    public final double wheelDiameter = 0.0;//Meters

    public final double countsPerRev = 0.0;//Encoder ticks per revolution for drive motors
    
    //These need to be in meters per second
    public final double maxVel = 0.0;
    public final double maxAccel = 0.0;
    public final double maxJerk = 0.0;

    //bunch of references to motors and such
    public CANSparkMax Drive_0 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);//Front left
    public CANSparkMax Drive_1 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);//Back left
    public CANSparkMax Drive_2 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);//Front right
    public CANSparkMax Drive_3 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);//Back right

    public CANEncoder Drive_E_0;
    public CANEncoder Drive_E_1;
    public CANEncoder Drive_E_2;
    public CANEncoder Drive_E_3;


    public WPI_TalonSRX Steering_0 = new WPI_TalonSRX(11);//Front left
    public WPI_TalonSRX Steering_1 = new WPI_TalonSRX(22);//Back left
    public WPI_TalonSRX Steering_2 = new WPI_TalonSRX(33);//Front right
    public WPI_TalonSRX Steering_3 = new WPI_TalonSRX(44);//Back right

    public CANSparkMax Elevator_0 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);

    public CANEncoder Elevator_E_0;

    public AHRS navX = new AHRS(SPI.Port.kMXP, (byte)60);

    public RobotMap() {
        //sets up stuff if necessary
        Drive_E_0 = Drive_0.getEncoder();
        Drive_E_1 = Drive_1.getEncoder();
        Drive_E_2 = Drive_2.getEncoder();
        Drive_E_3 = Drive_3.getEncoder();

        Elevator_E_0 = Elevator_0.getEncoder();

    }
}
