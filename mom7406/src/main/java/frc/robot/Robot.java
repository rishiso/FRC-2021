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
  private String m_driveSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  
  //Robot vars
  MecanumDrive m_drive;

  Joystick m_stick;

  SpeedController m_lift;
  SpeedController m_wheel;

  SpeedController leftFront;
  SpeedController rightFront;
  SpeedController leftBack;
  SpeedController rightBack;

  PullActuator m_actuator;

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

    m_drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);

    m_stick = new Joystick(RobotMap.JOYSTICK);

    m_lift = new VictorSP(RobotMap.LIFT_MOTOR);

    m_wheel = new VictorSP(RobotMap.LAZY_SUSAN);

    m_actuator = new PullActuator(RobotMap.PULL_ACTUATOR);
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
    m_driveSelected = m_chooser.getSelected();
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_driveSelected) {
      case defaultAuto:

        break;
      case autoTwo:

        break;
      case autoThree:

        break;
    }
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
    if (m_stick.getRawButton(RobotMap.SUSAN_RIGHT)) {
      m_wheel.set(.5);
    } else if (m_stick.getRawButton(RobotMap.SUSAN_LEFT)) {
      m_wheel.set(-.5);
    } else {
      m_wheel.stopMotor();
    }

    //Contols actuator on pull mechanism
    if (m_stick.getRawButtonReleased(RobotMap.PULL_ACTUATOR_BUTTON)) {
      m_actuator.switchMode();
    }

    if (m_stick.getRawButton(RobotMap.PULL_ACTUATOR_BUTTON)) {
      m_actuator.activate();
    } else {
      m_actuator.stop();
    }

    //Display data on SmartDashboard
    SmartDashboard.putNumber("Drive Power:", speedFactor);
    SmartDashboard.putNumber("Joystick X:", m_stick.getX());
    SmartDashboard.putNumber("Joystick Y:", -m_stick.getY());
    SmartDashboard.putNumber("Joystick Z:", m_stick.getZ());
    SmartDashboard.putString("Actuator Mode", m_actuator.mode);

  }

  @Override
  public void testPeriodic() {
  }
}
