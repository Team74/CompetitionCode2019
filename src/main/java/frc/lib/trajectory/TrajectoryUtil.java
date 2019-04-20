package frc.lib.trajectory;

import frc.lib.spline.*;
import frc.lib.trajectory.timing.*;
import frc.lib.utils.geometry.*;
import frc.lib.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryUtil {
    public static <S extends IPose2d<S>> Trajectory<S> mirror(final Trajectory<S> trajectory) {
        List<S> waypoints = new ArrayList<>(trajectory.length());
        for (int i = 0; i < trajectory.length(); ++i) {
            waypoints.add(trajectory.getState(i).mirror());
        }
        return new Trajectory<>(waypoints);
    }

    public static  <S extends IPose2d<S>> Trajectory<TimedState<S>> mirrorTimed(final Trajectory<TimedState<S>> trajectory) {
        List<TimedState<S>> waypoints = new ArrayList<>(trajectory.length());
        for (int i = 0; i < trajectory.length(); ++i) {
            TimedState<S> timedState = trajectory.getState(i);
            waypoints.add(new TimedState<S>(timedState.state().mirror(), timedState.t(), timedState.velocity(), timedState.acceleration()));
        }
        return new Trajectory<>(waypoints);
    }

    public static <S extends IPose2d<S>> Trajectory<S> transform(final Trajectory<S> trajectory, Pose2d transform) {
        List<S> waypoints = new ArrayList<>(trajectory.length());
        for (int i = 0; i < trajectory.length(); ++i) {
            waypoints.add(trajectory.getState(i).transformBy(transform));
        }
        return new Trajectory<>(waypoints);
    }

    public static Trajectory<Pose2dWithCurvature> trajectoryFromSplineWaypoints(final List<Pose2d> waypoints, double maxDx, double maxDy, double maxDTheta) {
        List<QuinticHermiteSpline> splines = new ArrayList<>(waypoints.size() - 1);
        for (int i = 1; i < waypoints.size(); ++i) {
            splines.add(new QuinticHermiteSpline(waypoints.get(i - 1), waypoints.get(i)));
        }
        return trajectoryFromSplines(splines, maxDx, maxDy, maxDTheta);
    }

    public static Trajectory<Pose2dWithCurvature> trajectoryFromSplines(final List<? extends Spline> splines, double maxDx, double maxDy, double maxDTheta) {
        return new Trajectory<>(SplineGenerator.parameterizeSplines(splines, maxDx, maxDy, maxDTheta));
    }
}