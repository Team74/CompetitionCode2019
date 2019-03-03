package frc.robot;

import java.util.HashMap;

import frc.robot.subsystems.BallManipulator;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Wrist;

public class StateMachine implements Updateable {

    private SubsystemManager mSubsystemManager;
    
    public String elevatorSetpoint = "Bottom";
    public HashMap<String, Double> elevatorSetpointList= new HashMap<String, Double>();
    //Possible values hoo boy there's a lot: Bottom, Intake, Low_Panel, Mid_Panel, High_Panel, Low_Ball, Mid_Ball, High_Ball, Cargo_Ball

    public String wristSetpoint = "Perpendicular";
    public HashMap<String, Integer> wristSetpointList= new HashMap<String, Integer>();
    //Possible values 3:  Parallel Perpendicular CargoDiagonal

    public String ballManipulatorTarget = "Hold";
    //Possible values 3:  Out In Hold

    public String panelManipulatorTarget = "In";
    //Possible values 2:  Out In

    public StateMachine(SubsystemManager subsystemManager) {

        mSubsystemManager = subsystemManager;

        elevatorSetpointList.put("Bottom", 0.0);
        elevatorSetpointList.put("Intake", 0.0);
        elevatorSetpointList.put("Low_Panel", 0.0);
        elevatorSetpointList.put("Mid_Panel", 0.0);
        elevatorSetpointList.put("High_Panel", 0.0);
        elevatorSetpointList.put("Low_Ball", 0.0);
        elevatorSetpointList.put("Mid_Ball", 0.0);
        elevatorSetpointList.put("High_Ball", 0.0);
        elevatorSetpointList.put("Cargo_Ball", 0.0);
        

        wristSetpointList.put("Stow", 0);
        wristSetpointList.put("Parallel", 200);
        wristSetpointList.put("CargoDiagonal", 2000);
        wristSetpointList.put("Perpendicular", 3190);
        
        {
            Double[] temp = elevatorSetpointList.values().toArray(new Double[0]); double[] temp2 = new double[temp.length]; for(int i = 0; i < temp.length; ++i) { temp2[i] = temp[i].doubleValue(); }     
            //mSubsystemManager.mElevator.setSetpoints((String[])elevatorSetpointList.keySet().toArray(), temp2);
            String[] temp3 = elevatorSetpointList.keySet().toArray(new String[0]); //String[] temp4 = new String[temp3.length]; for(int i = 0; i < temp3.length; ++i) { temp4[i] = temp3[i]; }         
            mSubsystemManager.mElevator.setSetpoints(temp3, temp2);
        }
        {
            Integer[] temp = wristSetpointList.values().toArray(new Integer[0]); int[] temp2 = new int[temp.length]; for(int i = 0; i < temp.length; ++i) { temp2[i] = temp[i].intValue(); } 
            String[] temp3 = wristSetpointList.keySet().toArray(new String[0]); //String[] temp4 = new String[temp3.length]; for(int i = 0; i < temp3.length; ++i) { temp4[i] = temp3[i]; }         
            mSubsystemManager.mWrist.setSetpoints(temp3, temp2);
        }
        
    }

    public void update(double dt) {

        if(mSubsystemManager.mElevator.currentState == Elevator.ElevatorState.MOVING) {
            assert wristSetpoint == "Perpendicular";
            assert ballManipulatorTarget == "Hold";
            assert panelManipulatorTarget == "In";
            //don't actually do much -- the Elevator is handling moving on its own, don't damage things

        } else if(mSubsystemManager.mElevator.currentState == Elevator.ElevatorState.HOLDING) {
            
            if(mSubsystemManager.mElevator.listedSetpoints_aliases.get(elevatorSetpoint) == mSubsystemManager.mElevator.currentTarget) {
                switch(1) {
                case 1:
                    if(mSubsystemManager.mWrist.listedSetpoints_aliases.get(wristSetpoint) != mSubsystemManager.mWrist.currentTarget) {
                        mSubsystemManager.mWrist.setTarget(wristSetpoint);
                        mSubsystemManager.mBallManipulator.currentState = BallManipulator.BallManipulatorState.HOLDING;
                        break;
                    }
                    if( (elevatorSetpoint == "INTAKE_BALL" && ballManipulatorTarget == "In") || (mSubsystemManager.mWrist.currentState == Wrist.WristState.HOLDING)) {
                        
                        if (mSubsystemManager.mBallManipulator.haveBall) { ballManipulatorTarget = "Hold"; }
                        
                        if(ballManipulatorTarget == "Out") {
                            mSubsystemManager.mBallManipulator.currentState = BallManipulator.BallManipulatorState.OUT;

                        } else if(ballManipulatorTarget == "In") {
                            mSubsystemManager.mBallManipulator.currentState = BallManipulator.BallManipulatorState.IN;

                        } else {
                            mSubsystemManager.mBallManipulator.currentState = BallManipulator.BallManipulatorState.HOLDING;
                        }
                    }
                }
            } else {
                mSubsystemManager.mBallManipulator.setState(BallManipulator.BallManipulatorState.HOLDING);

                if (mSubsystemManager.mWrist.currentState == Wrist.WristState.HOLDING && wristSetpoint == "Perpendicular"){
                    mSubsystemManager.mElevator.setTarget(elevatorSetpoint);
                } else {
                    mSubsystemManager.mWrist.setTarget(wristSetpoint); //should always be Perpendicular, really -- if bugs, check here
                }
            } 
        }
        else { //MANUAL CONTROL
            //put code here, someday
        }
    } 
}
/*abstract        if (m_stateMachine.elevatorSetpoint == "Low" && m_stateMachine.wristSetpoint == "Parallel" && m_buttons.get("1d_down")) {
            m_stateMachine.ballManipulatorState = "Intaking";
        } else if ()
        */