package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;

import frc.robot.behavior.AutonMaster;

import jaci.pathfinder.Waypoint;

public class Implemented_AutonMaster extends AutonMaster {


    public Waypoint[] currentPath;
    public String currentAuto;

    public Implemented_AutonMaster(SubsystemManager subsystem_manager){
        super(subsystem_manager);
    }

    public void update(double dt) {
    }
}
