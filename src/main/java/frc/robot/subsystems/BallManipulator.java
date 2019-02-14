//TODO: Measure load to know when we've picked up a ball. question: will there be a backstop?


package frc.robot.subsystems;

import frc.robot.Updateable;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;



public class BallManipulator implements Updateable{

    public RobotMap mRobotMap;  //reference to the original

    public WPI_TalonSRX mIntakeFront;
    public WPI_TalonSRX mIntakeBack;

    public final int kTimeoutMs = 30;

    private final double kCurrentThreshold = 0.0;

    public static enum BallManipulatorState {
        IN, OUT, HOLDING;
    }
    public BallManipulatorState currentState;

    public boolean haveBall = false;

    private double elapsedTime = 0.0;


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
        double averageCurrent = (mIntakeBack.getOutputCurrent() + mIntakeFront.getOutputCurrent() ) / 2;
        if (averageCurrent >= kCurrentThreshold) { elapsedTime += dt; }
        if (elapsedTime >= .2) {
            currentState = BallManipulatorState.HOLDING;
            haveBall = true;
        }
        switch(currentState) {
            case IN:
                mIntakeFront.set(1);
                mIntakeBack.set(1);
            break;
            case OUT:
            mIntakeFront.set(-1);
            mIntakeBack.set(-1);
            haveBall = false;
            break;
            case HOLDING:
                mIntakeFront.set(0);
                mIntakeBack.set(0);
            break;
            default:
                throw new RuntimeException("How did this happen? @BallIntake.java");
        }
    }
    
}