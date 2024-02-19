// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  //Input devices. In FRC Driver, check which USB the joystick is on
  public static final int joystickPort = 0;


  //Motors
  //Drivetrain
  public static final int driveMotorLFport = 1;
  public static final int driveMotorLRport = 2;
  public static final int driveMotorRFport = 3;
  public static final int driveMotorRRport = 4;

  //controls the max turning speed. 0 is unable to turn, 1 is full power.
  public static final double driveTrainMaxTurnSpeed = 0.8;
  public static final double driveTrainMaxDriveSpeed = 1;


  //Lifter
  public static final int lifterMotorPort = 8;
  //ArmExtender
  public static final int armExtenderMotorPort = 9;
  

}
