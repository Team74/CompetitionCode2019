package frc.robot.subsystems;

public interface Subsystem {

    public void readPeriodicInputs();

    public void writePeriodicOutputs();

    //Method to write subsystem data to dashboard
    public void outputTelemetry();

    public void stop();

    public void zeroSensors();
}