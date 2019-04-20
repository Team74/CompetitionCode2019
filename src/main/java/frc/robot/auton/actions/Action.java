package frc.robot.auton.actions;

/*
Template for all actions
*/

public interface Action {

    //Containts code to start action
    void start();

    //Run when action is done
    void done();

    //Define some criteria for when action is done here 
    boolean isFinished();
    
    //All code that neds to be updated goes here
    void update();
}