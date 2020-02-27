/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 

public class Robot extends TimedRobot {  
  
  //Dashboard vars
  private static final String defaultAuto = "Default";
  private static final String autoTwo = "Option 2";
  private static final String autoThree = "Option 3";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  
  //Robot vars
  MecanumDrive m_drive;
  Auto m_auto;

  Joystick m_stick;

  SpeedController m_lift;
  SpeedController m_wheel;

  SpeedController leftFront;
  SpeedController rightFront;
  SpeedController leftBack;
  SpeedController rightBack;

  double speedFactor;

  @Override
  public void robotInit() {
    //Runs once when robot boots
    m_chooser.setDefaultOption("Default Auto", defaultAuto);
    m_chooser.addOption("Auto 2", autoTwo);
    m_chooser.addOption("Auto 3", autoThree);
    SmartDashboard.putData("Auto choices:", m_chooser);

    leftFront = new VictorSP(RobotMap.DRIVE_FRONT_LEFT);
    rightFront = new VictorSP(RobotMap.DRIVE_FRONT_RIGHT);
    leftBack = new VictorSP(RobotMap.DRIVE_BACK_LEFT);
    rightBack = new VictorSP(RobotMap.DRIVE_BACK_RIGHT);

    m_stick = new Joystick(RobotMap.JOYSTICK);

    m_lift = new VictorSP(RobotMap.LIFT_MOTOR);

    m_wheel = new VictorSP(RobotMap.LAZY_SUSAN);


  
  }

  @Override
  public void robotPeriodic() {
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   */
  }

  @Override
  public void autonomousInit() {
    m_auto = new Auto(leftFront, leftBack, rightFront, rightBack);
    m_auto.setSafetyEnabled(false);
    m_autoSelected = m_chooser.getSelected();
    
    switch (m_autoSelected) {
      case defaultAuto:
        m_auto.forward(5);
        m_auto.left();
        m_auto.finished();
        break;
      case autoTwo:
        m_auto.forward(2);
        m_auto.finished();
        break;
      case autoThree:
        m_auto.forward(3);
        m_auto.finished();
        break;
    }
  }

  @Override
  public void autonomousPeriodic() {
    SmartDashboard.putString("Autonomous Status:", m_auto.action);
  }

  @Override
  public void teleopInit() {
    m_drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);
    super.teleopInit();
  }


  @Override
  public void teleopPeriodic() {
    //Use throttle to adjust drive speed
    //Ranges from 25% to 100% power
    speedFactor = m_stick.getThrottle();
    speedFactor *= -1;
    speedFactor += 1;
    speedFactor = .375 * speedFactor + .25;

    //Multiply inputs by value only if that specific input needs a different sensistivity
    m_drive.driveCartesian(speedFactor * m_stick.getX(), -speedFactor * m_stick.getY(), speedFactor * m_stick.getZ());

    //Controls pull up motor
    if (m_stick.getRawButton(RobotMap.PULL_UP)) {
      m_lift.set(-.5);
    } else if (m_stick.getRawButton(RobotMap.PULL_DOWN)) {
      m_lift.set(.5);
    } else {
      m_lift.stopMotor();
    }

    //Controls lazy susan
    if (m_stick.getRawButton(RobotMap.SPIN_RIGHT)) {
      m_wheel.set(.5);
    } else if (m_stick.getRawButton(RobotMap.SPIN_LEFT)) {
      m_wheel.set(-.5);
    } else {
      m_wheel.stopMotor();
    }

    SmartDashboard.putNumber("Drive Power:", speedFactor);
    SmartDashboard.putNumber("Joystick X:", m_stick.getX());
    SmartDashboard.putNumber("Joystick Y:", -m_stick.getY());
    SmartDashboard.putNumber("Joystick Z:", m_stick.getZ());
  }

  @Override
  public void testPeriodic() {
  }
}
