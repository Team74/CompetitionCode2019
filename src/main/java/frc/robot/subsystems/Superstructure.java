package frc.robot.subsystems;

public class Superstructure implements Subsystem {
    //Instance of the subsystem, gettable through the getInstance() method
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

    public void commandSuperstructure(double _newHeight, int _newAngle) {
        commandHeight(_newHeight);
        commandAngle(_newAngle);
    }

    public void commandHeight(double _newHeight) {
        targetHeight = _newHeight;
    }

    public void commandAngle(int _newAngle) {
        targetAngle = _newAngle;
    }

    public void output() {
        if (!isActive) {
            return;
        }
    }
}