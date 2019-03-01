package frc.lib.motorcontroller;

import com.revrobotics.*;

//Since the Talon remebers the previous setpoint, this class removes uneccessary set command by overriding the TalonSRX set fundtion
public class SparkExtended extends CANSparkMax {

    CANPIDController mController;
    CANEncoder mEncoder;

    private boolean isPosition; //type of PID -- pos or vel

    private double lastP = 0;
    private double lastI = 0;
    private double lastIZone = 0;
    private double lastD = 0;
    private double lastF = 0;

    private static int kSlotIDX = 0;
    private static double kMinOutput = -1;
    private static double kMaxOutput = 1;
    private static double maxVel = 5600;
    private static double minVel = 0.0;
    private static double maxAcc = 1000;
    private static double allowedError = 0.0;

    public static SparkExtended makeVelocitySpark(int canID, boolean invert) {
        return new SparkExtended(canID, invert, false, false, false);
    }

    public static SparkExtended makePositionMMSpark(int canID, boolean invert) {
        return new SparkExtended(canID, invert, true, true, true);
    }

    //note: if you want to slave it, spark.follow(CANSparkMax.ExternalFollower.kFollowerSparkMax, masterID);
    public SparkExtended(int canID, boolean invert, boolean coast, boolean motionMagic, boolean _isPosition) {
        super(canID, CANSparkMaxLowLevel.MotorType.kBrushless);
        isPosition = _isPosition;
        mController = getPIDController();
        mEncoder = getEncoder();

        restoreFactoryDefaults();
        setInverted(invert);
        setIdleMode(coast ? CANSparkMax.IdleMode.kCoast : CANSparkMax.IdleMode.kBrake );

        mEncoder.setPosition(0);
        if(motionMagic) {
            mController.setOutputRange(kMinOutput, kMaxOutput, kSlotIDX);
            mController.setSmartMotionMaxVelocity(maxVel, kSlotIDX);
            mController.setSmartMotionMinOutputVelocity(minVel, kSlotIDX);
            mController.setSmartMotionMaxAccel(maxAcc, kSlotIDX);
            mController.setSmartMotionAllowedClosedLoopError(allowedError, kSlotIDX);
        }

        mController.setP(lastP, kSlotIDX);      //TODO: See if we can still pass in kSlotIDX if we're not using MotionMagic
        mController.setIZone(lastIZone, kSlotIDX);
        mController.setI(lastI, kSlotIDX);
        mController.setD(lastD, kSlotIDX);
        mController.setFF(lastF, kSlotIDX);

    }

    public void setPIDCoeff(String whichCoefficient, double _value) {
        if (whichCoefficient.equalsIgnoreCase("P") && lastP != _value) {
            lastP = _value;
            mController.setP(_value);
        } else if (whichCoefficient.equalsIgnoreCase("I") && lastI != _value) {
            lastI = _value;
            mController.setI(_value);
        } else if (whichCoefficient.equalsIgnoreCase("D") && lastD != _value) {
            lastD = _value;
            mController.setD(_value);
        } else if (whichCoefficient.equalsIgnoreCase("F") && lastF != _value) {
            lastF = _value;
            mController.setFF(_value);
        } else if (whichCoefficient.equalsIgnoreCase("IZone") && lastF != _value) {
            lastIZone = _value;
            mController.setIZone(_value);
        }
    }

    public void setReference(double value) {
        if(isPosition) {
            mController.setReference(value, ControlType.kSmartMotion, kSlotIDX);
        } else {
            mController.setReference(value, ControlType.kVelocity);
        }
    }
}