package frc.robot.subsystems;

//Anticipatory class for the climber which consists of a dart linear actuator powered by a cim motor connected to a talonSRX

import frc.robot.RobotMap;
import frc.robot.SubsystemManager;
import frc.robot.Updateable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber implements Updateable {

    private SubsystemManager mSubsystemManager;
    private RobotMap mRobotMap;

    public TalonSRX climberMotor;

    public double kCurrentLimit;
    public boolean kCurrentFlag;

    public static enum ClimberState {
        Pushing,
        Retracting,
        Holding;
    }

    private double kPushing = 1;
    private double kRetracting = -1;

    public ClimberState currentState = ClimberState.Holding;

    public Climber(SubsystemManager _subsystemManager, RobotMap _robotMap) {
        mSubsystemManager = _subsystemManager;
        mRobotMap = _robotMap;
        climberMotor = mRobotMap.Climber_0;

        climberMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setState(ClimberState _climberState) {
        currentState = _climberState;
    }

    public void update(double dt) {
        switch(currentState) {
            case Pushing:
                climberMotor.set(ControlMode.PercentOutput, kPushing);
                break;
            case Retracting:
                climberMotor.set(ControlMode.PercentOutput, kRetracting);
                break;
            case Holding:
            climberMotor.set(ControlMode.PercentOutput, 0.0);
                break;
            default:

        }

    }
}