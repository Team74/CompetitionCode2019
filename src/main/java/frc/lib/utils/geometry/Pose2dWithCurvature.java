package frc.lib.utils.geometry;

public class Pose2dWithCurvature {
    protected static final Pose2dWithCurvature kIdentity = new Pose2dWithCurvature();

    public static final Pose2dWithCurvature identity() {
        return kIdentity;
    }

    protected final Pose2d pose;
    protected final double curvature;
    protected final double dcurvature_ds;

    public Pose2dWithCurvature() {
        pose = new Pose2d();
        curvature = 0.0;
        dcurvature_ds = 0.0;
    }

    public Pose2dWithCurvature(final Pose2d _pose, double _curvature) {
        pose = _pose;
        curvature = _curvature;
        dcurvature_ds = 0.0;
    }

    public Pose2dWithCurvature(final Pose2d _pose, double _curvature, double _dcurvature_ds) {
        pose = _pose;
        curvature = _curvature;
        dcurvature_ds = _dcurvature_ds;
    }

    public Pose2dWithCurvature(final Translation2d _translation, final Rotation2d _rotation, double _curvature) {
        pose = new Pose2d(_translation, _rotation);
        curvature = _curvature;
        dcurvature_ds = 0.0;
    }

    public Pose2dWithCurvature(final Translation2d _translation, final Rotation2d _rotation, double _curvature, double _dcurvature_ds) {
        pose = new Pose2d(_translation, _rotation);
        curvature = _curvature;
        dcurvature_ds = _dcurvature_ds;
    }

    public final Pose2d getPose() {
        return pose;
    }

    public double getCurvature() {
        return curvature;
    }

    public double getDCurvature() {
        return dcurvature_ds;
    }

    public final Translation2d getTranslation() {
        return getPose().getTranslation();
    }

    public final Rotation2d getRotation() {
        return getPose().getRotation();
    }

    public double distance(final Pose2dWithCurvature other) {
        return getPose().distance(other.getPose());
    }

    public Pose2dWithCurvature transformBy(Pose2d _transform) {
        return new Pose2dWithCurvature(getPose().transformBy(_transform), getCurvature(), getDCurvature());
    }

}
