package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DrivetrainSubsystem;

public class SimpleAuto extends SequentialCommandGroup{
    
    //This is a work in progresss, this shouldn't be used at this time.

    public SimpleAuto(DrivetrainSubsystem drivetrain){
        addCommands(
            new CmdDrive(0.5, 0, 2, drivetrain)
        );
    }

   

}
