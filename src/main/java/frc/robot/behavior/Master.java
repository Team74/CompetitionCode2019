package frc.robot.behavior;

import frc.robot.Updateable;
import frc.robot.SubsystemManager;
/*
The Goal of this class is to create a 'controller' that gives commands to the SubsystemManager. Various subclasses -- TeleopMaster, AutonMaster, etc. -- will extend Master and actually implement the update() method, as well as whatever else they need to do their job (TeleopMaster needs to get input from the drivers, for instance). SubsystemManager, meanwhile, will contain all of the actual implementations of functions. Essentially, the actions the robot can take end up in SubsystemManager, while the controller telling the robot to take those actions ends up in Master.
*/
abstract public class Master implements Updateable {

    Master() {

    }
    //abstract public void update();   //Gives commands to the SubsystemManager; called from within SubsystemManager's update method
    //now comes from the Updateable interface

}
