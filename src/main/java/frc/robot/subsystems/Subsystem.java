package frc.robot.subsystems;

public interface Subsystem {
    //Method all subsytems will have to write motor outputs
    void output();

    //Starts the subsystem, run during autoinit
    void start();

    //Stops updating the subsystem
    void stop();
}