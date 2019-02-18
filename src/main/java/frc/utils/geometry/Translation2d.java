package frc.utils.geometry;

//Stores x, y positions as vectors, or transformations from the origin

public class Translation2d {
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
    
    //Inverse returns a Translations2d that undoes the Translation2d it is called from, hence -x, -y
    public Translation2d inverse() {
        return new Translation2d(-x, -y);
    }

    //The "norm" of a transform is th Euclidean distance in x and y. AKA the length of the vector 
    public double norm() {
        return Math.hypot(x, y);
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
    public double distance(final Translation2d other) {
        return inverse().translateBy(other).norm();
    }

    public Translation2d getTranslation() {
        return this;
    }
}