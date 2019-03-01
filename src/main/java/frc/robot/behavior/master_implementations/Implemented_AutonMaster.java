package frc.robot.behavior.master_implementations;

import frc.robot.SubsystemManager;

import frc.robot.behavior.AutonMaster;

import frc.robot.behavior.master_implementations.pathfinder.Paths;
import frc.robot.behavior.master_implementations.pathfinder.PathFollower;

import jaci.pathfinder.Waypoint;

public class Implemented_AutonMaster extends AutonMaster {

    public PathFollower pathfollower;
    public Paths paths = new Paths();

    public Waypoint[] currentPath;
    public String currentAuto;

    public Implemented_AutonMaster(SubsystemManager subsystem_manager){
        super(subsystem_manager);

        //get currentAuto from the drive station or have it hardcoded
        currentAuto = "Test Path";
        currentPath = paths.m_paths.get(currentAuto);
        pathfollower = new PathFollower(subsystem_manager);
        pathfollower.pathToTrajectory(currentPath, true); //not sure about what to pass in for isReversed
    }

    public void update(double dt) {
        pathfollower.update(dt);
    }
}
