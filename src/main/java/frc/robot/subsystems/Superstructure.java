package frc.robot.subsystems;

public class Superstructure implements Subsystem {
    private static Superstructure kInstance = null;

    //Is the subsytem running
    private boolean isActive = false;

    //Elevator height. Spark wants a double
    private double targetHeight = 0.0;
    //Wrist angle. Talon wants an int
    private int targetAngle = 0;

    public static Superstructure getInstance() {
        if (kInstance == null) {
            kInstance = new Superstructure();
        }
        return kInstance;
    }
    
    private Superstructure() {

    }

    public void start() {
        if (!isActive) {
            isActive = true;
        }
    }

    public void stop() {
        if (isActive) {
            isActive = false;
        }
    }

    public void setSuperstructure(double _newHeight, int _newAngle) {
        targetHeight = _newHeight;
        targetAngle = _newAngle;
    }

    public void output() {
        if (!isActive) {
            return;
        }
    }
}