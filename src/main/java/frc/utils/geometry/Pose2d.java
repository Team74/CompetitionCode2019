package frc.utils.geometry;

import frc.utils.geometry.Translation2d;
import frc.utils.geometry.Rotation2d;

public class Pose2d {

    protected static final Pose2d kIdentity = new Pose2d();

    public static final Pose2d identity( ){
        return kIdentity;
    }

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

    public Translation2d getTranslation() {
        return mTranslation;
    }

    public Rotation2d getRotation() {
        return mRotation;
    }

}