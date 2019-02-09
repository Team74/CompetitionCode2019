package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveModule;
import frc.robot.InputManager;

import frc.robot.behavior.TeleopMaster;

import frc.utils.Utilities;

public class Test1_TeleopMaster extends TeleopMaster {

    SwerveModule currentMotor;
    String output_extra;

    double k_deadband = 0.05;


    public Test1_TeleopMaster(SubsystemManager subsystem_manager, InputManager input_manager) {
        super(subsystem_manager, input_manager);

        currentMotor = m_subsystem_manager.m_drivetrain.lb;
        output_extra = "lb: ";
     }

    public void update(double dt) {  
        /*
        if(m_input_manager.m_buttons.get("0a")) {
            currentMotor = m_subsystem_manager.m_drivetrain.lb;
            output_extra = "lb: ";
        }
        else if(m_input_manager.m_buttons.get("0x")) {
            currentMotor = m_subsystem_manager.m_drivetrain.lf;
            output_extra = "lf: ";
        }        
        else if(m_input_manager.m_buttons.get("0y")) {
            currentMotor = m_subsystem_manager.m_drivetrain.rf;
            output_extra = "rf: ";
        }       
        else if(m_input_manager.m_buttons.get("0b")) {
            currentMotor = m_subsystem_manager.m_drivetrain.rb;
            output_extra = "rb: ";
        }        
        double encoderCount = currentMotor.rotate_motor.getSelectedSensorPosition(0);
        double encoderVelocity = currentMotor.rotate_motor.getSelectedSensorVelocity(0);
        System.out.println(output_extra + encoderCount);
        if (m_input_manager.m_buttons.get("0r_bumper")){
            currentMotor.setMotors(
                0,
                0
                );
        } else if (m_input_manager.m_buttons.get("0l_bumper")) {
            currentMotor.setMotorsPercentOutput(
                m_input_manager.m_joysticks.get("0rx"),
                m_input_manager.m_joysticks.get("0ly"));
        } else {
            currentMotor.rotate_motor.stopMotor();
        }
        */

        double leftX = -m_input_manager.m_joysticks.get("0lx");
        double leftY = m_input_manager.m_joysticks.get("0ly");
        double rightX = m_input_manager.m_joysticks.get("0rx");




        //System.out.println("leftX: " + leftX);
        //System.out.println("leftY: " + leftY);
        //System.out.println("rightX: " + rightX);

        
        double magnitude = Math.hypot(Utilities.handleDeadband(leftX, k_deadband), Utilities.handleDeadband(leftY, k_deadband));
        double angle = Math.atan2(Utilities.handleDeadband(leftX, k_deadband), Utilities.handleDeadband(leftY, k_deadband));
        double rotation = Utilities.handleDeadband(rightX, k_deadband);

        //System.out.println("Magnitude: " + magnitude);
        //System.out.println("Magnitude: " + magnitude);
        //System.out.println("Rotation: " + rotation);
        //System.out.println("Angle: " + angle);
        //System.out.println("Rotation: " + rotation);
        //System.out.println("\n");

        double gyroVal = m_subsystem_manager.m_drivetrain.gyro.getAngle();
        gyroVal *= Math.PI / 180;

        System.out.println(gyroVal);

        angle -= gyroVal;

        angle %= 2*Math.PI;
        angle += (angle < -Math.PI) ? 2*Math.PI : 0;
        angle -= (angle > Math.PI) ? 2*Math.PI : 0;

        rotation /= 30;

        m_subsystem_manager.m_drivetrain.setMove(magnitude, angle, rotation);
        //m_subsystem_manager.m_drivetrain.setMove(handleDeadband(leftX), handleDeadband(leftY), rotation);
    }
}
