package frc.robot.subsystems;

import frc.robot.subsystems.Subsystem;

public class BallIntake extends Subsystem {
    public static BallIntake kInstance = null;

    public static BallIntake getInstance() {
        if (kInstance == null) {
            kInstance = new BallIntake();
        }
        return kInstance;
    }
    private BallIntake() {

    }
}