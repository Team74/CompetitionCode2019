package frc.lib.motorcontroller;

import frc.lib.motorcontroller.TalonSetOverride;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonSRXBuilder {

    private static int kTimeoutMS = 30;

    //Defualt configs to use
    private static NeutralMode neutralMode = NeutralMode.Brake;

    private static double openLoopRampRate = 0.0;//In seconds from 0 to full power
    private static double closedLoopRampRate = 0.0;//In seconds fron 0 to full power

    public static TalonSRX buildDefaultTalon(int canID){
        return makeTalon(canID);
    }

    public static TalonSRX buildSlavedTalon(int canID, int masterID) {
        TalonSRX talon = makeTalon(canID);
        talon.set(ControlMode.Follower, masterID);
        return talon;
    }

    private static TalonSRX makeTalon(int canID){
        TalonSRX talon = new TalonSetOverride(canID);
        talon.setNeutralMode(neutralMode);
        talon.configOpenloopRamp(openLoopRampRate, kTimeoutMS);
        talon.configClosedloopRamp(closedLoopRampRate, kTimeoutMS);

        //Set max and min outputs for each direction
        talon.configNominalOutputForward(0, kTimeoutMS);
        talon.configPeakOutputForward(1.0, kTimeoutMS);

        talon.configNominalOutputReverse(0, kTimeoutMS);
        talon.configPeakOutputReverse(-1.0, kTimeoutMS);

        return talon;
    }
    
}