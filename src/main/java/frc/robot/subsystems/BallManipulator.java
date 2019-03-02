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

    private final double kCurrentThreshold = 50.0;

    private final double kInSpeed = .2;

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
        if (mIntakeBack.getOutputCurrent() >= kCurrentThreshold) {
            currentState = BallManipulatorState.HOLDING;
            haveBall = true;
        }
        
        switch(currentState) {
            case IN:
                mIntakeFront.set(kInSpeed);
                mIntakeBack.set(-kInSpeed);
            break;
            case OUT:
            mIntakeFront.set(-1);
            mIntakeBack.set(1);
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