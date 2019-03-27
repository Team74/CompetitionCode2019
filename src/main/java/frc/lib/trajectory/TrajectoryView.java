package frc.lib.trajectory;

import frc.lib.trajectory.Trajectory;
import frc.lib.trajectory.TrajectorySamplePoint;

public interface TrajectoryView<S> {
    public TrajectorySamplePoint<S> sample(final double interpolant);

    public double firstInterpolant();

    public double lastInterpolant();

    public Trajectory<S> trajectory();
}