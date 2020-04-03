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
  private static final String autoTwo = "Idle";
  private String m_driveSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  
  //Robot vars
  MecanumDrive m_drive;

  Joystick m_stick;

  SpeedController m_lift;
  SpeedController m_wheel;
  PullActuator m_actuator;

  SpeedController leftFront;
  SpeedController rightFront;
  SpeedController leftBack;
  SpeedController rightBack;

  double speedFactor;
  Timer tim;

  @Override
  public void robotInit() {
    //Runs once when robot boots
    m_chooser.setDefaultOption("Default Auto", defaultAuto);
    m_chooser.addOption("Idle Auto", autoTwo);
    SmartDashboard.putData("Auto choices:", m_chooser);

    leftFront = new VictorSP(RobotMap.DRIVE_FRONT_LEFT);
    rightFront = new VictorSP(RobotMap.DRIVE_FRONT_RIGHT);
    leftBack = new VictorSP(RobotMap.DRIVE_BACK_LEFT);
    rightBack = new VictorSP(RobotMap.DRIVE_BACK_RIGHT);

    m_drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);
    m_drive.setSafetyEnabled(false);

    m_stick = new Joystick(RobotMap.JOYSTICK);

    m_lift = new VictorSP(RobotMap.LIFT_MOTOR);

    m_wheel = new VictorSP(RobotMap.LAZY_SUSAN);

    m_actuator = new PullActuator(RobotMap.PULL_ACTUATOR);

    tim = new Timer();
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
    tim.start();
  }

  @Override
  public void autonomousPeriodic() {
    if (m_driveSelected == defaultAuto) {
      if (tim.get() <= 2.0) {
        m_drive.driveCartesian(0, 0.1, 0);
      } else {
        m_drive.stopMotor();
      }
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

    //Speedfactor controls sensitivity
    m_drive.driveCartesian(-speedFactor * m_stick.getX(), -speedFactor * m_stick.getY(), speedFactor * m_stick.getZ());

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
    if (m_stick.getRawButton(RobotMap.PULL_ACTUATOR_OPEN)) {
      m_actuator.open();
    } else if (m_stick.getRawButton(RobotMap.PULL_ACTUATOR_CLOSE)) {
      m_actuator.close();
    } else {
      m_actuator.stop();
    }

    //Display data on SmartDashboard
    SmartDashboard.putNumber("Drive Power:", speedFactor);
    SmartDashboard.putString("Actuator Status:", m_actuator.mode);
    SmartDashboard.putNumber("Actuator Position:", m_actuator.get());
    SmartDashboard.putNumber("Joystick X:", -speedFactor * m_stick.getX());
    SmartDashboard.putNumber("Joystick Y:", -speedFactor * m_stick.getY());
    SmartDashboard.putNumber("Joystick Z:", speedFactor * m_stick.getZ());
  }

  @Override
  public void testPeriodic() {
  }
}
