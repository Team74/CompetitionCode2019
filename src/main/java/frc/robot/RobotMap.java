package frc.robot;

import frc.lib.motorcontroller.TalonSRXBuilder;
import frc.lib.motorcontroller.WrappedSparkMax;

import com.revrobotics.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
/**
 * This class sets up all the different things related to the PWM, plus some other stuff. Use getInstance to ensure that there is only one instance of all of this stuff at a time;
 */
public class RobotMap {
    //Singular instance of the class
    private static RobotMap kInstance = null;

    //bunch of references to motors and such
    public CANSparkMax Drive_0 = new CANSparkMax(11, CANSparkMaxLowLevel.MotorType.kBrushless);//Front left
    public CANSparkMax Drive_1 = new CANSparkMax(22, CANSparkMaxLowLevel.MotorType.kBrushless);//Back left
    public CANSparkMax Drive_2 = new CANSparkMax(33, CANSparkMaxLowLevel.MotorType.kBrushless);//Front right
    public CANSparkMax Drive_3 = new CANSparkMax(44, CANSparkMaxLowLevel.MotorType.kBrushless);//Back right

    public CANEncoder Drive_E_0;
    public CANEncoder Drive_E_1;
    public CANEncoder Drive_E_2;
    public CANEncoder Drive_E_3;


    public TalonSRX Steering_0 = TalonSRXBuilder.buildDefaultTalon(6);//Front left Steering Motor
    public TalonSRX Steering_1 = TalonSRXBuilder.buildDefaultTalon(3);//Back left Steering Motor
    public TalonSRX Steering_2 = TalonSRXBuilder.buildDefaultTalon(4);//Front right Steering Motor
    public TalonSRX Steering_3 = TalonSRXBuilder.buildDefaultTalon(5);//Back right Steering Motor

    public WrappedSparkMax Elevator_0 = new WrappedSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);

    public TalonSRX Intake_0 = TalonSRXBuilder.buildDefaultTalon(1);//Front
    public TalonSRX Intake_1 = TalonSRXBuilder.buildDefaultTalon(2);//Back

    public TalonSRX Wrist_0 = TalonSRXBuilder.buildDefaultTalon(7);//Wrist

    public TalonSRX Climber_0 = TalonSRXBuilder.buildDefaultTalon(8);//Climber

    public TalonSRX Puller_0 = TalonSRXBuilder.buildDefaultTalon(9);//Climber Puller

    public AHRS navX = new AHRS(SPI.Port.kMXP, (byte)60);

    public static RobotMap getInstance() {
        if (kInstance == null) {
            kInstance = new RobotMap();
        }
        return kInstance;
    }

    private RobotMap() {
        //sets up stuff if necessary
        Drive_E_0 = Drive_0.getEncoder();
        Drive_E_1 = Drive_1.getEncoder();
        Drive_E_2 = Drive_2.getEncoder();
        Drive_E_3 = Drive_3.getEncoder();

        Elevator_E_0 = Elevator_0.getEncoder();

    }
}