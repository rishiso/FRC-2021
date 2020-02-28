package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class PullServo extends Servo {

    public PullServo(Integer port) {
        super(port);
    }

    public void Switch(boolean isButtonPressed) {
        if (isButtonPressed) {
            if (getAngle() == 0.5) {
                set(1.0);
            } else {
                set(0.5);
            }
        }
    }
}