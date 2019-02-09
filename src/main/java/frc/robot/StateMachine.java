package frc.robot;

import java.util.HashMap;


public class StateMachine {

    enum Subsystems {
        ELEVATOR, WRIST, BALL_MANIPULATOR, PANEL_MANIPULATOR; 
    }

    enum DisabledStates {
        ENABLED, DISABLED;
    }

    enum ElevatorTargets {
        BOTTUM, INTAKEBALL, LOWHATCH, MIDHATCH, HIGHHATCH, LOWBALL, MIDBALL, HIGHBALL, CARGOBALL; 
    }

    enum ElevatorStates {
        HOLDING, MOVING, MANUAL;
    }

    enum WristTargets {
        OUT, IN, CARGO;
    }

    enum WristStates {
        HOLDING, MOVING, MANUAL;
    }
    
    enum BallManipulatorTargets {
        INTAKING, OUTTAKE, HOLDING;
    }

    enum PanelManipulatorTargets {
        IN, OUT;
    }

    public HashMap<Subsystems, Enum> currentState = new HashMap<>();
    public HashMap<Subsystems, Enum> desiredState = new HashMap<>();
    public HashMap<Subsystems, Enum> subsystemTargets = new HashMap<>();

    public StateMachine(){
        currentState.put(Subsystems.ELEVATOR, ElevatorStates.HOLDING);
        currentState.put(Subsystems.WRIST, WristStates.HOLDING);

        desiredState = currentState;

        subsystemTargets.put(Subsystems.ELEVATOR, ElevatorTargets.BOTTUM);
        subsystemTargets.put(Subsystems.WRIST, WristTargets.IN);
        subsystemTargets.put(Subsystems.BALL_MANIPULATOR, BallManipulatorTargets.HOLDING);
        subsystemTargets.put(Subsystems.PANEL_MANIPULATOR, PanelManipulatorTargets.IN);
    }

    public void update(){
        updateElevator(desiredState.get(Subsystems.ELEVATOR), subsystemTargets.get(Subsystems.ELEVATOR));
        updateWrist(desiredState.get(Subsystems.WRIST), subsystemTargets.get(Subsystems.WRIST));
        updateBallManipulator(subsystemTargets.get(Subsystems.BALL_MANIPULATOR));
        updatePanelManipulator(subsystemTargets.get(Subsystems.PANEL_MANIPULATOR));

    }

    public void updateElevator(ElevatorStates desiredState, ElevatorTargets target){
        switch(desiredState) {
            case HOLDING:
                //Set elevator to target
            case MOVING:
                //Set elevator to target
            case MANUAL:
                //In this state we want to use the joystick to drive the elevator
            default:
                System.out.println("Something went wrong");

        }
    }

    public void updateWrist(WristStates desiredState, WristTargets target){

    }

    public void updateBallManipulator(BallManipulatorTargets target){

    }

    public void updatePanelManipulator(PanelManipulatorTargets target){

    }
}