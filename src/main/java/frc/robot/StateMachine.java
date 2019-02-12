package frc.robot;

import java.util.HashMap;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Wrist;

public class StateMachine implements Updateable {

    private SubsystemManager m_subsystemManager;
    private Elevator elev;
    private Wrist wrist;


    public String elevatorSetpoint = "Bottom";
    public HashMap<String, Double> elevatorSetpointList= new HashMap<String, Double>();
    //Possible values hoo boy

    public String wristSetpoint = "Perpendicular";
    public HashMap<String, Integer> wristSetpointList= new HashMap<String, Integer>();
    //Possible values 3:  Parallel Perpendicular CargoDiagonal

    public String ballManipulatorTarget = "Hold";
    //Possible values 3:  Out In Hold

    public String panelManipulatorTarget = "In";
    //Possible values 2:  Out In

    public StateMachine(SubsystemManager subsystemManager) {
        m_subsystemManager = subsystemManager;
        elev = m_subsystemManager.m_elevator;
        wrist = m_subsystemManager.m_wrist;

        {
            Double[] temp = elevatorSetpointList.values().toArray(new Double[0]); double[] temp2 = new double[temp.length]; for(int i = 0; i < temp.length; ++i) { temp2[i] = temp[i]; }     
            m_subsystemManager.m_elevator.setSetpoints((String[])elevatorSetpointList.keySet().toArray(), temp2);
        }
        {
            Integer[] temp = elevatorSetpointList.values().toArray(new Integer[0]); int[] temp2 = new int[temp.length]; for(int i = 0; i < temp.length; ++i) { temp2[i] = temp[i]; }     
            m_subsystemManager.m_wrist.setSetpoints((String[])wristSetpointList.keySet().toArray(), temp2);
        }
    }

    public void update(double dt) {

        if(elev.currentState == Elevator.ElevatorState.MOVING) {
            assert wristSetpoint == "Perpendicular";
            assert ballManipulatorTarget == "Hold";
            assert panelManipulatorTarget == "In";
            //don't actually do much -- the Elevator is handling moving on its own, don't damage things
        } else if(elev.currentState == Elevator.ElevatorState.HOLDING) {
            if(elev.listedSetpoints_aliases.get(elevatorSetpoint) == elev.currentTarget) {
                if(elevatorSetpoint == "Low_Ball" || (wrist.currentState == Wrist.WristState.HOLDING && wrist.listedSetpoints_aliases.get(wristSetpoint) == wrist.currentTarget)) {
                    if(ballManipulatorTarget == "OUT") {

                    } else if(ballManipulatorTarget == "IN") {

                    } else {
                        m_subsystemManager
                    }
                }
            } else {
                
            }
        } else { //MANUAL CONTROL
            //put code here, someday
        }
    }


}
/*abstract        if (m_stateMachine.elevatorSetpoint == "Low" && m_stateMachine.wristSetpoint == "Parallel" && m_buttons.get("1d_down")) {
            m_stateMachine.ballManipulatorState = "Intaking";
        } else if ()
        */