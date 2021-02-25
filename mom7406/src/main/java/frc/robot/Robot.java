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
//import edu.wpi.first.networktables.*;
import com.revrobotics.*;

public class Robot extends TimedRobot {  
  
  //Autonomous vars
  private int pathSelected;
  private final SendableChooser<Integer> pathChooser = new SendableChooser<>();

  //Color Wheel control
  private static final String red = "Blue";
  private static final String blue = "Red";
  private static final String yellow = "Green";
  private static final String green = "Yellow";
  private final SendableChooser<String> colorChooser = new SendableChooser<>();
  
  //Robot vars
  private MecanumDrive m_drive;

  private Joystick m_stick;

  private SpeedController m_lift;
  private SpeedController m_wheel;
  private PullActuator m_actuator;

  private SpeedController leftFront;
  private SpeedController rightFront;
  private SpeedController leftBack;
  private SpeedController rightBack;

  private double speedFactor;
  //private NetworkTable limelight;
  private ColorSensorV3 m_colorSensor;
  private ColorMatcher cMatcher;
  private Timer tim;

  @Override
  public void robotInit() {
    //Runs once when robot boots
    pathChooser.setDefaultOption("Path 1", 1);
    pathChooser.addOption("Path 2", 2);
    pathChooser.addOption("Path 3", 3);
    SmartDashboard.putData("Auto choices:", pathChooser);
    tim = new Timer();

    colorChooser.setDefaultOption("Red", red);
    colorChooser.addOption("Blue", blue);
    colorChooser.addOption("Yellow", yellow);
    colorChooser.addOption("Green", green);
    SmartDashboard.putData("Color Choices:", colorChooser);

    leftFront = new VictorSP(RobotMap.DRIVE_FRONT_LEFT);
    rightFront = new VictorSP(RobotMap.DRIVE_FRONT_RIGHT);
    leftBack = new VictorSP(RobotMap.DRIVE_BACK_LEFT);
    rightBack = new VictorSP(RobotMap.DRIVE_BACK_RIGHT);

    m_drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);

    m_stick = new Joystick(RobotMap.JOYSTICK);

    m_lift = new VictorSP(RobotMap.LIFT_MOTOR);

    m_wheel = new VictorSP(RobotMap.LAZY_SUSAN);

    m_actuator = new PullActuator(RobotMap.PULL_ACTUATOR);

    //limelight = NetworkTableInstance.getDefault().getTable("limelight");

    m_colorSensor = new ColorSensorV3(RobotMap.COLOR_SENSOR);
    cMatcher = new ColorMatcher();
    cMatcher.addColors();
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
    pathSelected = pathChooser.getSelected();
    tim.reset();
    tim.start();
  }

  @Override
  public void autonomousPeriodic() {
    //Drives forward for 3 seconds
    double curTime = tim.get();

    //Path 1
    if (pathSelected == 1) {
      if (curTime < 3) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 6) m_drive.driveCartesian(0, .25, .3);
      else if (curTime < 8) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 11) m_drive.driveCartesian(0, .25, -.3);
      else if (curTime < 13) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 15) m_drive.driveCartesian(0, .25, -.2);
      else if (curTime < 18) m_drive.driveCartesian(0, .4, 0);
    }

    //Path 2
    else if (pathSelected == 2) {
      if (curTime < 1) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 3) m_drive.driveCartesian(0, 0, -.2);
      else if (curTime < 8) m_drive.driveCartesian(0, .3, .2);
      else if (curTime < 14) m_drive.driveCartesian(0, .25, -.3);
      else if (curTime < 19) m_drive.driveCartesian(0, .3, .2);
      else if (curTime < 21) m_drive.driveCartesian(0, .25, -.1);
    }

    //Path 3
    else {
      if (curTime < 1.5) m_drive.driveCartesian(0, .25, -.2);
      else if (curTime < 3) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 5) m_drive.driveCartesian(0, 0, .25);
      else if (curTime < 8) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 10) m_drive.driveCartesian(0, 0, .25);
      else if (curTime < 13) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 15) m_drive.driveCartesian(0, 0, .2);
      else if (curTime < 18) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 20) m_drive.driveCartesian(0, 0, -.2);
      else if (curTime < 21.5) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 23.5) m_drive.driveCartesian(0, 0, -.2);
      else if (curTime < 26.5) m_drive.driveCartesian(0, .25, 0);
      else if (curTime < 28.5) m_drive.driveCartesian(0, 0, .25);
      else if (curTime < 30.5) m_drive.driveCartesian(0, .25, 0);
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
    } else if (m_stick.getRawButton(RobotMap.AUTOMATIC_COLOR_SPIN) && colorChooser.getSelected() != cMatcher.colorCheck(m_colorSensor.getColor())){
      m_wheel.set(.5);
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
    SmartDashboard.putString("Detected Color:", cMatcher.colorCheck(m_colorSensor.getColor()));
  }

  @Override
  public void testPeriodic() {
  }
}
