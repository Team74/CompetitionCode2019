package frc.lib.utils.geometry;

import frc.lib.utils.geometry.Translation2d;
import frc.lib.utils.geometry.Rotation2d;

public class Pose2d {

    protected static final Pose2d kIdentity = new Pose2d();

    public static final Pose2d identity( ){
        return kIdentity;
    }

    private final static double kEps = 1E-9;

    protected final Translation2d mTranslation;
    protected final Rotation2d mRotation;

    public Pose2d() {
        mTranslation = new Translation2d();
        mRotation = new Rotation2d();
    }

    public Pose2d(double x, double y, final Rotation2d _rotation) {
        mTranslation = new Translation2d(x, y);
        mRotation = _rotation;
    }

    public Pose2d(final Translation2d _translation, final Rotation2d _rotation) {
        mTranslation = _translation;
        mRotation = _rotation;
    }

    public Pose2d(final Pose2d other) {
        mTranslation = new Translation2d(other.mTranslation);
        mRotation = new Rotation2d(other.mRotation);
    }

    public static Pose2d fromTranslation(final Translation2d _translation) {
        return new Pose2d(_translation, new Rotation2d());
    }

    public static Pose2d fromRotation(final Rotation2d _rotation) {
        return new Pose2d(new Translation2d(), _rotation);
    }

    public Pose2d transformBy(final Pose2d other) {
        return new Pose2d(mTranslation.translateBy(other.mTranslation.rotateBy(mRotation)),
        mRotation.rotateBy(other.mRotation));
    }

    /**
     * The inverse of this transform "undoes" the effect of translating by this transform.
     *
     * @return The opposite of this transform.
     */
    public Pose2d inverse() {
        Rotation2d rotation_inverted = mRotation.inverse();
        return new Pose2d(mTranslation.inverse().rotateBy(rotation_inverted), rotation_inverted);
    }

    public Pose2d mirror() {
        return new Pose2d(new Translation2d(getTranslation().x(), -getTranslation().y()), getRotation().inverse());
    }

    public Pose2d getPose() {
        return this;
    }

    /*
        Obtain a new Pose2d from a (constant curvature) velocity. See:
        https://github.com/strasdat/Sophus/blob/master/sophus/se2.hpp
    */
    public static Pose2d exp(final Twist2d delta) {
        double sin_theta = Math.sin(delta.dtheta);
        double cos_theta = Math.cos(delta.dtheta);
        double s, c;
        if (Math.abs(delta.dtheta) < kEps) {
            s = 1.0 - 1.0 / 6.0 * delta.dtheta * delta.dtheta;
            c = .5 * delta.dtheta;
        } else {
            s = sin_theta / delta.dtheta;
            c = (1.0 - cos_theta) / delta.dtheta;
        }
        return new Pose2d(new Translation2d(delta.dx * s - delta.dy * c, delta.dx * c + delta.dy * s),
                new Rotation2d(cos_theta, sin_theta, false));
    }

    /*
        Logical inverse of the above.
    */
    public static Twist2d log(final Pose2d transform) {
        final double dtheta = transform.getRotation().getRadians();
        final double half_dtheta = 0.5 * dtheta;
        final double cos_minus_one = transform.getRotation().cos() - 1.0;
        double halftheta_by_tan_of_halfdtheta;
        if (Math.abs(cos_minus_one) < kEps) {
            halftheta_by_tan_of_halfdtheta = 1.0 - 1.0 / 12.0 * dtheta * dtheta;
        } else {
            halftheta_by_tan_of_halfdtheta = -(half_dtheta * transform.getRotation().sin()) / cos_minus_one;
        }
        final Translation2d translation_part = transform.getTranslation()
                .rotateBy(new Rotation2d(halftheta_by_tan_of_halfdtheta, -half_dtheta, false));
        return new Twist2d(translation_part.x(), translation_part.y(), dtheta);
    }

    public double distance(final Pose2d other) {
        return Pose2d.log(inverse().transformBy(other)).norm();
    }


    public Translation2d getTranslation() {
        return mTranslation;
    }

    public Rotation2d getRotation() {
        return mRotation;
    }

}