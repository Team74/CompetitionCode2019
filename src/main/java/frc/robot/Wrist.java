package frc.robot;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Wrist {

    public RobotMap mRobotMap;

    public WPI_TalonSRX wristMotor;

    public Wrist(RobotMap _robotMap){
        mRobotMap = _robotMap;
        wristMotor = mRobotMap.WWrist_0;

    }

}