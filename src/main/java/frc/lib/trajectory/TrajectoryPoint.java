package frc.lib.trajectory;

public class TrajectoryPoint<S> {
    protected final S entry;
    protected final int index;

    public TrajectoryPoint(final S _entry, final int _index) {
        entry = _entry;
        index = _index;
    }

    public S entry() {
        return entry;
    }

    public int index() {
        return index;
    }
}