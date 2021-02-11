/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C; 

//RobotMap is where you can store ports and mappings

public final class RobotMap {
    //Rio mappings
    public final static int DRIVE_FRONT_LEFT = 0;
    public final static int DRIVE_FRONT_RIGHT = 1;
    public final static int DRIVE_BACK_LEFT = 2;
    public final static int DRIVE_BACK_RIGHT = 3;
    public final static int LIFT_MOTOR = 4;
    public final static int LAZY_SUSAN = 5;
    public final static int PULL_ACTUATOR = 6;
    public final static I2C.Port COLOR_SENSOR = I2C.Port.kOnboard; 

    //Joystick mappings
    public final static int JOYSTICK = 0;
    public final static int PULL_UP = 5;
    public final static int PULL_DOWN = 3;
    public final static int SUSAN_RIGHT = 4;
    public final static int SUSAN_LEFT = 6;
    public final static int PULL_ACTUATOR_OPEN = 11;
    public final static int PULL_ACTUATOR_CLOSE = 12;
}
