package frc.robot.subsystems;

//Anticipatory class for the climber which consists of a dart linear actuator powered by a cim motor connected to a talonSRX

import frc.robot.RobotMap;
import frc.robot.Updateable;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber implements Updateable {

    private RobotMap mRobotMap;

    public TalonSRX climberMotor;

    public double kCurrentLimit;
    public boolean kCurrentFlag;

    public Climber(RobotMap _robotMap) {
        mRobotMap = _robotMap;
        climberMotor = mRobotMap.Climber_0;
    }

    public void update(double dt) {

    }
}