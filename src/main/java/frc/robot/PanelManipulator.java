//TODO: Measure load to know when we've picked up a ball. question: will there be a backstop?


package frc.robot;

import frc.robot.Updateable;


public class PanelManipulator implements Updateable{

    public RobotMap mRobotMap;  //reference to the original


    //implementation -- probably pneumatics

    enum PanelManipulatorState {
        IN, OUT
    }
    PanelManipulatorState currentState;


    public PanelManipulator(RobotMap robotMap){
        mRobotMap = robotMap;
    
        //implementation -- initialization
    }

    public void setState(PanelManipulatorState state) {
        currentState = state;
    }

    public void update(double dt) {
        //implementation -- move it somehow
    }
    
}