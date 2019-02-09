package frc.robot;

import java.util.HashMap;


public class StateMachine {

    enum Subsystems {
        ELEVATOR, WRIST, BALL_MANIPULATOR, PANEL_MANIPULATOR; 
    }

    enum DisabledStates {
        ENABLED, DISABLED;
    }

    enum ElevatorStates {
        HOLDING, MOVING_TO_POSITION, MANUAL; 
    }

    enum WristStates {
        HOLDING, MOVING_TO_POSITION, MANUAL;
    }
    
    enum BallManipulatorStates {
        INTAKING, OUTTAKE, HOLDING;
    }

    enum PanelManipulatorStates {
        IN, OUT;
    }

    public HashMap<Subsystems, Enum> currentState = new HashMap<>();
    public HashMap<Subsystems, Enum> desiredState = new HashMap<>();

    public StateMachine(){
        currentState.put(Subsystems.ELEVATOR, ElevatorStates.HOLDING);
        currentState.put(Subsystems.WRIST, WristStates.HOLDING);
        currentState.put(Subsystems.BALL_MANIPULATOR, BallManipulatorStates.HOLDING);
        currentState.put(Subsystems.PANEL_MANIPULATOR, PanelManipulatorStates.IN);

        desiredState = currentState;
    }

    public void update(){
        if ( desiredState != currentState){
        
        } else {
            System.out.println("Do nothing");
        }
    }

    public void updateElevator(){

    }

    public void updateWrist(){

    }

    public void updateBallManipulator(){

    }

    public void updatePanelManipulator(){

    }
}