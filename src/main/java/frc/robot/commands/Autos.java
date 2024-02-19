// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static CommandBase exampleAuto(
    DrivetrainSubsystem drivetrain, 
    MotorSubsystem lifter, 
    MotorSubsystem armextender, 
    PneumaticSubsystem grabber) {
    
      return Commands.sequence(
      
      
      new CmdDrive(0.8,0,0.5,drivetrain),
      new CmdDrive(0,-0.3, 0.5,drivetrain),
      new CmdTogglePneumatic(grabber),
      new WaitCommand(4),
      new CmdTogglePneumatic(grabber)
      
      );


  }



  public static CommandBase doNothing() {
    
      return Commands.sequence(
      
      );


  }




  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
