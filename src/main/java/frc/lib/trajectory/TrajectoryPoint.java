package frc.lib.trajectory;

public class TrajectoryPoint<S> {
    protected final S state;
    protected final int index;

    public TrajectoryPoint(final S _state, final int _index) {
        state = _state;
        index = _index;
    }

    public S state() {
        return state;
    }

    public int index() {
        return index;
    }
}