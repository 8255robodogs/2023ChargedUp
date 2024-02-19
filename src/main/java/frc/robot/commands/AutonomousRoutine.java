package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;


public class AutonomousRoutine extends SequentialCommandGroup{
    
    public AutonomousRoutine(DrivetrainSubsystem drivetrain, MotorSubsystem lifter, MotorSubsystem armextender, PneumaticSubsystem grabber){
        addCommands(

            
            new CmdSpinMotorNegative(2.2, lifter),
            
            new CmdSpinMotorNegative(1.8, armextender),
            new CmdTogglePneumatic(grabber),
            new CmdSpinMotorPositive(1.9, armextender),
            new CmdSpinMotorPositive(2, lifter),
            
            
            new CmdDrive(-.8,0,2,drivetrain), //This is the drive backwards
            new WaitCommand(.5),
            new CmdDrive(0, .6, .5, drivetrain) //This is the turn


        );
    }
    
}