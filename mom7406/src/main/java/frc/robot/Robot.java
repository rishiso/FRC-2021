/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;


public class Robot extends TimedRobot {
  //Initialize all vars that do not directly involve robot

  //Vars used in dashboard
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //Robot vars
  private MecanumDrive m_drive;
  private Joystick m_stick;
  private SpeedController m_lift;
  private SpeedController m_wheel;
  private SpeedController leftFront;
  private SpeedController rightFront;
  private SpeedController leftBack;
  private SpeedController rightBack;
  private double speedFactor;


  @Override
  public void robotInit() {
    //Runs once when robot boots

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftFront = new VictorSP(RobotMap.DRIVE_FRONT_LEFT);
    rightFront = new VictorSP(RobotMap.DRIVE_FRONT_RIGHT);
    leftBack = new VictorSP(RobotMap.DRIVE_BACK_LEFT);
    rightBack = new VictorSP(RobotMap.DRIVE_BACK_RIGHT);

    m_drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);

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
  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */

    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        
        break;
      case kDefaultAuto:
      default:

        break;
    }
  }


  @Override
  public void teleopPeriodic() {
    //Use throttle to adjust drive speed
    //Ranges from 25% to 50% power
    speedFactor = m_stick.getThrottle();
    speedFactor *= -1;
    speedFactor += 1;
    speedFactor = .125 * speedFactor + .25;

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
      m_wheel.set(1);
    } else if (m_stick.getRawButton(RobotMap.SPIN_LEFT)) {
      m_wheel.set(-1);
    } else {
      m_wheel.stopMotor();
    }


  }

  @Override
  public void testPeriodic() {
  }
}
