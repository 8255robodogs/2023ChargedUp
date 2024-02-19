package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class CmdDrive extends CommandBase{
    private DrivetrainSubsystem drivetrain;
    private double forwardSpeed;
    private double turnSpeed;
    private double secondsToDrive;
    private long startTime;
    private float secondsElapsed = 0.0f;
    

    public CmdDrive(double forwardSpeed, double turnSpeed, double seconds, DrivetrainSubsystem drivetrain){
        this.forwardSpeed = -forwardSpeed;
        this.turnSpeed = turnSpeed;
        secondsToDrive = seconds;
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
        
    }

    @Override
    public void initialize(){
        drivetrain.ArcadeDrive(forwardSpeed, turnSpeed);
        startTime = System.nanoTime();
    }

    @Override 
    public void execute(){
        drivetrain.ArcadeDrive(forwardSpeed, turnSpeed);

        //time tracking
        long currentTime = System.nanoTime();
        long nanoSecondsElapsed = currentTime - startTime;
        secondsElapsed = nanoSecondsElapsed / 1000000000; //divide by 1 billion

    }

    @Override
    public boolean isFinished(){
        //we have to return a true value at the end of isFinished to move onto the "end" function
        if(secondsElapsed >= secondsToDrive){
            return true;
        }else{
            return false;
        }
        
    }

    @Override
    public void end(boolean interrupted){
        drivetrain.ArcadeDrive(0, 0);
    }

 

}
