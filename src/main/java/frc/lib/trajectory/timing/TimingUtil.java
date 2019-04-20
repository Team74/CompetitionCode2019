package frc.lib.trajectory.timing;

import frc.lib.trajectory.*;
import frc.lib.utils.geometry.*;
import frc.lib.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

//Basically a class to handle the time parameterization of trajectories
public class TimingUtil {
    public static <S extends State<S>> Trajectory<TimedState<S>> timeParameterizeTrajectory(
            boolean reverse,
            final DistanceView<S> distanceView,
            double stepSize,
            final List<TimingConstraints<S>> constraints,
            double startVelocity,
            double endVelocity,
            double maxVelocity,
            double maxAbsAcceleration,
            double maxDecceleration,
            int slowdownChunks) {
        final int numStates = (int) Math.ceil(distanceView.lastInterpolant() / stepSize + 1);
        List<S> states= new ArrayList<>(numStates);
        for (int i = 0; i < numStates; ++i) {
            states.add(distanceView.sample(Math.min(i * stepSize, distanceView.lastInterpolant())).state());
        }
        return timeParameterizeTrajectory(reverse, states, constraints, startVelocity, endVelocity, maxVelocity, maxAbsAcceleration, maxDecceleration, slowdownChunks);
    }
    
    public static <S extends State<S>> Trajectory<TimedState<S>> timeParameterizeTrajectory(
            boolean reverse,
            final List<S> states,
            final List<TimingConstraints<S>> constraints,
            double startVelocity,
            double endVelocity,
            double maxVelocity,
            double maxAbsAcceleration,
            double maxDecceleration,
            int slowdownChunks) {
        List<ConstrainedState<S>> constraintStates = new ArrayList<>(states.size());
        final double kEpsilon = 1e-6;

        // Forward pass. We look at pairs of consecutive states, where the start state has already been velocity
        // parameterized (though we may adjust the velocity downwards during the backwards pass). We wish to find an
        // acceleration that is admissible at both the start and end state, as well as an admissible end velocity. If
        // there is no admissible end velocity or acceleration, we set the end velocity to the state's maximum allowed
        // velocity and will repair the acceleration during the backward pass (by slowing down the predecessor).
        ConstrainedState<S> predecessor = new ConstrainedState<>();
        predecessor.state = states.get(0);
        predecessor.distance = 0.0;
        predecessor.maxVelocity = startVelocity;
        predecessor.minAcceleration = -maxAbsAcceleration;
        predecessor.maxAcceleration = maxAbsAcceleration;
        for (int i = 0; i < states.size(); ++i) {
            //Add the new state
            constraintStates.add(new ConstrainedState<>());
            ConstrainedState<S> constraintState = constraintStates.get(i);
            constraintState.state = states.get(i);
            final double ds = constraintState.state.distance(predecessor.state);
            constraintState.distance = ds + predecessor.distance;
        }
    }

    protected static class ConstrainedState<S extends State<S>> {
        public S state;
        public double distance;
        public double maxVelocity;
        public double minAcceleration;
        public double maxAcceleration;
    }
}