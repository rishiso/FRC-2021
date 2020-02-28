package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class MechDrive extends MecanumDrive {

    String action = "";

    public MechDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }

    public void forward(Integer secs) {
        action = "FORWARD";
        driveCartesian(0, .5, 0);
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopMotor();
    }

    public void right() {
        action = "RIGHT";
        driveCartesian(0, 0, -.5);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopMotor();

    }
    public void left() {
        action = "LEFT";
        driveCartesian(0, 0, .5);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopMotor();
    }

    public void finished() {
        action = "IDLE";
    }

}