//TODO: Measure load to know when we've picked up a ball. question: will there be a backstop?

package frc.robot.subsystems;

import frc.robot.Updateable;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;



public class BallManipulator implements Updateable{

    public RobotMap mRobotMap;  //reference to the original

    public TalonSRX mIntakeFront;
    public TalonSRX mIntakeBack;

    public final int kTimeoutMs = 30;

    private final double kCurrentLimit = 10.0;

    private final double kInSpeed = .4;
    private final double kOutSpeedFront = 1.0;
    private final double kOutSpeedBack = 1.0;


    public static enum BallManipulatorState {
        IN, OUT, HOLDING;
    }
    public BallManipulatorState currentState = BallManipulatorState.HOLDING;

    public boolean haveBall = false;


    public BallManipulator(RobotMap robotMap){
        mRobotMap = robotMap;
    
        mIntakeFront = mRobotMap.Intake_0;
        mIntakeBack  = mRobotMap.Intake_1;

        mIntakeFront.configFactoryDefault(kTimeoutMs);
        mIntakeBack.configFactoryDefault(kTimeoutMs);

        mIntakeFront.setNeutralMode(NeutralMode.Brake);
        mIntakeBack.setNeutralMode(NeutralMode.Brake);
    }

    public void setState(BallManipulatorState state) {
        currentState = state;
    }

    public void update(double dt) {
        switch(currentState) {
            case IN:
                if (kCurrentLimit <= Math.abs(mIntakeBack.getOutputCurrent())) {
                    currentState = BallManipulatorState.HOLDING;
                    mIntakeFront.set(ControlMode.PercentOutput, 0);
                    mIntakeBack.set(ControlMode.PercentOutput, 0);
                    haveBall = true;
                } else {
                    mIntakeFront.set(ControlMode.PercentOutput, kInSpeed);
                    mIntakeBack.set(ControlMode.PercentOutput, -kInSpeed);
                }
            break;
            case OUT:
                mIntakeFront.set(ControlMode.PercentOutput, -kOutSpeedFront);
                mIntakeBack.set(ControlMode.PercentOutput, kOutSpeedBack);
                haveBall = false;
            break;
            case HOLDING:
                mIntakeFront.set(ControlMode.PercentOutput, 0);
                mIntakeBack.set(ControlMode.PercentOutput, 0);
                break;
            default:
                throw new RuntimeException("How did this happen? @BallIntake.java");
        }
    }
    
}