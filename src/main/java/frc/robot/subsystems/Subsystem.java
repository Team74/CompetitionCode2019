package frc.robot.subsystems;

public interface Subsystem {

    public void readPeriodicInputs();

    public void writePeriodicOutputs();

    //Method to write subsystem data to dashboard
    public void outputTelemetry();

    public void start();

    public void stop();

    public boolean isActive();

    public void zeroSensors();
}