package frc.lib.utils.geometry;

import frc.lib.utils.Utilities;
//Stores x, y positions as vectors, or transformations from the origin

public class Translation2d implements ITranslation2d<Translation2d> {
    protected static final Translation2d kIdentity = new Translation2d();

    public static final Translation2d identity() {
        return kIdentity;
    }

    protected double x;
    protected double y;
    
    public Translation2d(){
        x = 0.0;
        y = 0.0;
    }

    public Translation2d(double _x, double _y){
        x = _x;
        y = _y;
    }

    public Translation2d(final Translation2d start, final Translation2d end) {
        x = end.x - start.x;
        y = end.y - start.y;
    }

    public Translation2d(final Translation2d other) {
        x = other.x;
        y = other.y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void setX(double _x){
        x = _x;
    }

    public void setY(double _y){
        y = _y;
    }

    //You can combine transforms by adding the x and y shifts AKA adding vectors
    public Translation2d translateBy(final Translation2d other) {
        return new Translation2d(x + other.x, y + other.y);
    }

    //You can rotate Translation2d's https://en.wikipedia.org/wiki/Rotation_matrix
    public Translation2d rotateBy(final Rotation2d _rotation) {
        return new Translation2d(x * _rotation.cos() - y * _rotation.sin(), x * _rotation.sin() + y * _rotation.cos());
    }

    public Rotation2d direction() {
        return new Rotation2d(x, y, true);
    }
    
    //Inverse returns a Translations2d that undoes the Translation2d it is called from, hence -x, -y
    public Translation2d inverse() {
        return new Translation2d(-x, -y);
    }

    @Override
    public Translation2d interpolate(final Translation2d other, double i) {
        if (i <= 0.0) {
            return new Translation2d(this);
        } else if (i >= 1.0) {
            return new Translation2d(other);
        }
        return extrapolate(other, i);
    }

    public Translation2d extrapolate(final Translation2d other, double e) {
        return new Translation2d(e * (other.x - x) + x, e * (other.y - y) + y);
    }

    //The "norm" of a transform is th Euclidean distance in x and y. AKA the length of the vector 
    public double norm() {
        return Math.hypot(x, y);
    }

    public boolean epsilonEquals(final Translation2d other, double epsilon) {
        return Utilities.epsilonEquals(x(), other.x(), epsilon) && Utilities.epsilonEquals(y(), other.y(), epsilon);
    }

    //Implementation of the dot product for two points or "vector points"
    public static double dot(Translation2d a, Translation2d b) {
        return a.x * b.x + a.y * b.y;
    }

    //Implementation of the cross product for two points or "vector points"
    public static double cross(final Translation2d a, final Translation2d b) {
        return a.x * b.y - a.y * b.x;
    }

    //Finds the distance of a straight line between the point of the Translation2d this is called from and other
    @Override
    public double distance(final Translation2d other) {
        return inverse().translateBy(other).norm();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof Translation2d)) return false;
        return distance((Translation2d)other) < Utilities.kEpsilon;
    }

    @Override
    public Translation2d getTranslation() {
        return this;
    }
}