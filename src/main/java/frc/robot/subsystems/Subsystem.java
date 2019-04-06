package frc.robot.subsystems;

public interface Subsystem {
    //Calculates output
    void output();

    //Starts the subsystem, run during autoinit
    void start();

    //Stops updating the subsystem
    void stop();

    //UPdates the Subsystems
    void update(double dt);
}