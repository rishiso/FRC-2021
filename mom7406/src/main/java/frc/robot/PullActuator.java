package frc.robot;
import edu.wpi.first.wpilibj.Servo;

public class PullActuator extends Servo {

    String mode = "CLOSE";
    
    public PullActuator(Integer port) {
        super(port);
        setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    }

    public void open() {
        setSpeed(1.0);
    }

    public void close() {
        setSpeed(-1.0);
    }
    
    public void stop() {
        setSpeed(0.0);
    }

    public void switchMode() {
        if (mode == "CLOSE") {
            mode = "OPEN";
        } else {
            mode = "CLOSE";
        }
    }

    public void activate() {
        if (mode == "CLOSE") {
            close();
        } else {
            open();
        }
    }

}
