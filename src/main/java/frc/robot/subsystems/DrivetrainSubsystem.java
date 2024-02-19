package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DrivetrainSubsystem extends SubsystemBase{

    private double maxDriveSpeed = 1.0;
    private double maxTurnSpeed = 0.8;

    //Get all 4 motors by their channel IDs
    private WPI_VictorSPX drivetrainLFMotor = new WPI_VictorSPX(Constants.driveMotorLFport);
    private WPI_VictorSPX drivetrainLRMotor = new WPI_VictorSPX(Constants.driveMotorLRport);
    private WPI_VictorSPX drivetrainRFMotor = new WPI_VictorSPX(Constants.driveMotorRFport);
    private WPI_VictorSPX drivetrainRRMotor = new WPI_VictorSPX(Constants.driveMotorRRport);


    //Put the motors into groups so they can run together
    private MotorControllerGroup drivetrainLeftMotors = new MotorControllerGroup(drivetrainLFMotor,drivetrainLRMotor);
    private MotorControllerGroup drivetrainRightMotors = new MotorControllerGroup(drivetrainRFMotor,drivetrainRRMotor);

    //Create a "differential drive" style drivetrain we can work with
    private DifferentialDrive m_differentialDrive = new DifferentialDrive(drivetrainRightMotors,drivetrainLeftMotors);

    SlewRateLimiter slewDrive = new SlewRateLimiter(3);
    SlewRateLimiter slewTurn = new SlewRateLimiter(3);


    //Setup the Encoders. These understand how far the wheels travel.
    //private Encoder m_rightEncoder;
    //private Encoder m_leftEncoder;



    //We call this code to make the robot drive.
    public void ArcadeDrive(double forward, double rotate){
        rotate = -rotate;



        //m_differentialDrive.arcadeDrive(forward, rotate);
        m_differentialDrive.arcadeDrive(slewDrive.calculate(forward), slewTurn.calculate(rotate));
    }

/*

    public void ResetEncoders(){
        m_leftEncoder.reset();
        m_rightEncoder.reset();
    }

    public double getAverageEncoderDistance() {
        double leftDistance = m_leftEncoder.getDistance();
        double rightDistance = m_rightEncoder.getDistance();
        double averageDistance = (leftDistance + rightDistance) / 2;
        
        return averageDistance;
    }

*/

    public DrivetrainSubsystem(boolean invertLeftMotors, boolean invertRightMotors){
        drivetrainLeftMotors.setInverted(invertLeftMotors);
        drivetrainRightMotors.setInverted(invertRightMotors);

        //m_leftEncoder = new Encoder(3,4,invertRightMotors);
        //m_rightEncoder = new Encoder(1,2,invertLeftMotors);

    }

    public void setMaxDriveSpeed(double speed){
        if(speed < 0){
            speed = Math.abs(speed);
        }

        if(speed > 1){
            speed = 1;
        }
        
        maxDriveSpeed = speed;
    }

    public double getMaxSpeed(){
        return maxDriveSpeed;
    }

    public void setMaxTurnSpeed(double speed){
        if(speed < 0){
            speed = Math.abs(speed);
        }

        if(speed > 1){
            speed = 1;
        }
        
        maxTurnSpeed = speed;
    }

    public double getTurnSpeed(){
        return maxDriveSpeed;
    }


    

}
