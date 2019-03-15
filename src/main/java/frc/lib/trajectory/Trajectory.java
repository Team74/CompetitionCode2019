package frc.lib.trajectory;

import frc.lib.trajectory.TrajectoryPoint;

import java.util.List;
import java.util.ArrayList;

public class Trajectory<S> {
    protected final List<TrajectoryPoint<S>> points;

    //Empty trajectory
    public Trajectory() {
        points = new ArrayList<TrajectoryPoint<S>>();
    }
    //Trajectory with List of object S
    public Trajectory(final List<S> entries) {
        points =  new ArrayList<TrajectoryPoint<S>>();
        for (int i = 0; i < entries.size(); ++i) {
            points.add(new TrajectoryPoint<>(entries.get(i), i));
        }
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int length() {
        return points.size();
    }

    public TrajectoryPoint<S> getPoint(final int index) {
        return points.get(index);
    }

    public S getEntry(final int index) {
        return points.get(index).entry();
    }

    public S getFirstEntry() {
        return points.get(0).entry();
    }

    public S getLastEntry() {
        return points.get(points.size() - 1).entry();
    }
}