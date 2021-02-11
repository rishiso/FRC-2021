package frc.robot;

import edu.wpi.first.wpilibj.Servo;


public class PullActuator extends Servo {
    String mode = "";
    
    public PullActuator(Integer port) {
        super(port);
        super.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    }

    public void open() {
        mode = "OPEN";
        super.setSpeed(1.0);
    }

    public void close() {
        mode = "CLOSE";
        super.setSpeed(-1.0);
    }

    public void stop() {
        mode = "IDLE";
        super.stopMotor();
    }

}
