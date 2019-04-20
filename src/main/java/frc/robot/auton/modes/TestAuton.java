package frc.robot.auton.modes;

import frc.robot.auton.AutonBase;
import frc.robot.auton.actions.*;

import frc.lib.trajectory.TrajectoryGenerator;
import frc.lib.trajectory.TrajectoryGenerator.Trajectories;

public class TestAuton extends AutonBase {

    private final Trajectories trajectories = TrajectoryGenerator.getInstance().getTrajectories();

    public TestAuton() {

    }

    //Fill this with actions
    @Override
    protected void routine() {
        runAction(new TestAction(1.0));
    }
}