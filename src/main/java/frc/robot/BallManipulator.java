//TODO: Measure load to know when we've picked up a ball. question: will there be a backstop?


package frc.robot;

import frc.robot.Updateable;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;



public class BallManipulator implements Updateable{

    public RobotMap mRobotMap;  //reference to the original

    public WPI_TalonSRX mIntakeFront;
    public WPI_TalonSRX mIntakeBack;

    public final int kTimeoutMs = 30;

    enum BallManipulatorState {
        INTAKING, OUTTAKING, HOLD
    }
    BallManipulatorState currentState;


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
            case INTAKING:
                mIntakeFront.set(1);
                mIntakeBack.set(1);
            break;
            case OUTTAKING:
            mIntakeFront.set(-1);
            mIntakeBack.set(-1);
            break;
            case HOLD:
                mIntakeFront.set(0);
                mIntakeBack.set(0);
            break;
            default:
                throw new RuntimeException("How did this happen? @BallIntake.java");
        }
    }
    
}