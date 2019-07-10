package frc.lib.motorcontroller;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/** 
 * Class to contain all 3 parts of the sparkMax for easy access in code
 * Also provides duplicate command protection
 */

public class WrappedSpark {
    private final CANSparkMax motor;
    private final CANPIDController controller;
    private final CANEncoder encoder;

    /**
     * List of the gains for the pid slots 0-3.
     * P = 0, I = 1, D = 2, FF = 3
     */
    private double[][] gains;

    private IdleMode lastIdleMode;

    private double lastValue = 0.0;
    private ControlType lastControlType = ControlType.kDutyCycle;
    private int lastSlotID = 0;
    private double lastArbFeedforward = 0.0;

    public WrappedSpark(int _deviceID) {
        this(_deviceID, MotorType.kBrushless);
    }

    public WrappedSpark(int _deviceID, MotorType _motorType) {
        motor = new CANSparkMax(_deviceID, _motorType);
        controller = motor.getPIDController();
        encoder = motor.getEncoder();

        lastIdleMode = IdleMode.fromId(_deviceID);
    }

    /**
     * @return The SparkMax object
     */
    public CANSparkMax getSparkMax() {
        return motor;
    }

    /**
     * @return The PIDController object
     */
    public CANPIDController getPIDController() {
        return controller;
    }

    /**
     * @return The encoder object
     */
    public CANEncoder getEncoder() {
        return encoder;
    }

    /**
     * @param _idleMode IdleMode to set motor to
     * @return CANError.kOK is successful
     */
    public CANError setIdleMode(IdleMode _idleMode) {
        if (_idleMode == lastIdleMode) return CANError.kOK;
        return motor.setIdleMode(_idleMode);
    }

    /**
     * Clears all sticky faults
     * @return CANError.kOK if successful
     */
    public CANError clearFaults() {
        return motor.clearFaults();
    }

    /**
     * @return The position of the encoder
     */
    public double getPosition() {
        return encoder.getPosition();
    }

    /**
     * @return The velocity in Rotations Per Minute
     */
    public double getVelocity() {
        return encoder.getVelocity();
    }

    public CANError setP(double _gain, int _slotID) {
        if (gains[_slotID][0] == _gain) return CANError.kOK;
        gains[_slotID][0] = _gain;
        return controller.setP(gains[_slotID][0], _slotID);
    }

    public CANError setI(double _gain, int _slotID) {
        if (gains[_slotID][1] == _gain) return CANError.kOK;
        gains[_slotID][1] = _gain;
        return controller.setI(gains[_slotID][1], _slotID);
    }

    public CANError setD(double _gain, int _slotID) {
        if (gains[_slotID][2] == _gain) return CANError.kOK;
        gains[_slotID][2] = _gain;
        return controller.setD(gains[_slotID][2], _slotID);
    }

    public CANError setF(double _gain, int _slotID) {
        if (gains[_slotID][3] == _gain) return CANError.kOK;
        gains[_slotID][3] = _gain;
        return controller.setFF(gains[_slotID][3], _slotID);
    }

    /**
     * Set the spark to a basic duty cycle
     * @param _value A double between -1 and 1 to set the motor to
     * @return CANError.kOK if successful
     */
    public CANError setReference(double _value) {
        return setReference(_value, ControlType.kDutyCycle, 0, 0.0);
    }

    /**
     * 
     * @param _value Demand for the ControlType
     * @param _controlType ControlType to use
     * @return CANError.kOK if successful
     */
    public CANError setReference(double _value, ControlType _controlType) {
        return setReference(_value, _controlType, 0, 0.0);
    }

    /**
     * 
     * @param _value Demand for ControlType
     * @param _controlType ControlType to use
     * @param _slotID Slot ID to use, between 0-3
     * @return CANError.kOK if successful
     */
    public CANError setReference(double _value, ControlType _controlType, int _slotID) {
        return setReference(_value, _controlType, _slotID, 0.0);
    }

    /**
     * 
     * @param _value Demand for ControlType
     * @param _controlType ControlType to use
     * @param _slotID Slot ID to use, between 0-3
     * @param _arbFeedforward Value between -1 and 1 to add to the output
     * @return CANError.kOK if successful
     */
    public CANError setReference(double _value, ControlType _controlType, int _slotID, double _arbFeedforward) {
        if (lastValue != _value || lastControlType != _controlType ||
            lastSlotID != _slotID || lastArbFeedforward != _arbFeedforward) {
            lastValue = _value;
            lastControlType = _controlType;
            lastSlotID = _slotID;
            lastArbFeedforward = _arbFeedforward;
            return controller.setReference(_value, _controlType, _slotID, _arbFeedforward);
        }
        return CANError.kOK;
        
    }
}