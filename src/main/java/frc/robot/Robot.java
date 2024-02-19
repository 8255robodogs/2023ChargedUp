// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenixpro.configs.MotorOutputConfigs;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CameraServerCvJNI;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutonomousRoutine;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  //input
  Joystick joystick = new Joystick(0);
  XboxController gamepad = new XboxController(1);

  //input - joystick axis values
  private double driveValue = 0;
  private double turnValue = 0;
  private double throttleValue = 0;
  //input - joystick buttons
  boolean triggerPressed;
  boolean button1Pressed;
  boolean button2Pressed;
  boolean button3Pressed;
  boolean button4Pressed;
  boolean button5Pressed;
  boolean button6Pressed;
  boolean button7Pressed;
  boolean button8Pressed;
  boolean button9Pressed;
  boolean button10Pressed;
  boolean button11Pressed;
  boolean button12Pressed;
  

  double gamepadLeftStickForward;
  double gamepadRightStickForward;
  boolean gamepadRightBumper;


  //systems
  DrivetrainSubsystem drivetrain = new DrivetrainSubsystem(true, false);
  MotorSubsystem lifter = new MotorSubsystem(Constants.lifterMotorPort, "lifter", false);
  MotorSubsystem armExtender = new MotorSubsystem(Constants.armExtenderMotorPort, "armExtender", false);
  PneumaticSubsystem grabber = new PneumaticSubsystem(2, 3, true);
  //DoubleSolenoid bottomSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,0,1);
  //DoubleSolenoid topSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,2,3);
  
  
  //variables
  boolean triggerReset = true;
  boolean button2Reset = true;
  boolean armExtenderLimitHit;
  
  //limit switches
  DigitalInput armExtenderExtendLimitSwitch = new DigitalInput(0);


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    UsbCamera camera = CameraServer.startAutomaticCapture();
    camera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 15);
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    System.out.println("Robot initiated.");
    //bottomSolenoid.set(Value.kReverse);
    //topSolenoid.set(Value.kReverse);

    m_autonomousCommand = new AutonomousRoutine(drivetrain, lifter, armExtender, grabber);

    armExtender.setMaxSpeed(1);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    
    //update the values of our inputs
    driveValue = joystick.getY();
    turnValue = joystick.getZ();
    throttleValue = -joystick.getThrottle();
    throttleValue = (throttleValue+1)/2;
    throttleValue = Math.max(throttleValue,0.5);

    //adjust drive and turn values based on throttle
    driveValue = driveValue*throttleValue;
    turnValue= turnValue*throttleValue;

    triggerPressed = joystick.getRawButton(1);
    button2Pressed = joystick.getRawButton(2);
    button3Pressed = joystick.getRawButton(3);
    button4Pressed = joystick.getRawButton(4);
    button5Pressed = joystick.getRawButton(5);
    button6Pressed = joystick.getRawButton(6);
    button7Pressed = joystick.getRawButton(7);
    button8Pressed = joystick.getRawButton(8);
    button9Pressed = joystick.getRawButton(9);
    button10Pressed = joystick.getRawButton(10);
    button11Pressed = joystick.getRawButton(11);
    button12Pressed = joystick.getRawButton(12);



    //game controller inputs
    gamepadLeftStickForward = -gamepad.getLeftY();
    gamepadRightStickForward = -gamepad.getRightY();
    gamepadRightBumper = gamepad.getRightBumper();


    //write out our inputs to the dashboard
    SmartDashboard.putNumber("drivetrain driveValue", driveValue);
    SmartDashboard.putNumber("drivetrain turnValue", turnValue);
    SmartDashboard.putNumber("throttleValue", throttleValue);
    SmartDashboard.putBoolean("triggerPressed", joystick.getRawButton(1));
    for(int i=2;i<=12;i++){
      SmartDashboard.putBoolean("button"+i+"Pressed", joystick.getRawButton(i));
    }

    SmartDashboard.putNumber("gamepadLeftStickForward", gamepadLeftStickForward);
    SmartDashboard.putNumber("gamepadRightStickForward", gamepadRightStickForward);
    SmartDashboard.putBoolean("gamepadRightBumper", gamepadRightBumper);

    //limit switch
    armExtenderLimitHit = !armExtenderExtendLimitSwitch.get();
    SmartDashboard.putBoolean("armExtenderLimitHit", armExtenderLimitHit);


    //debugging the arm extender
    SmartDashboard.putNumber("Extender Speed", armExtender.getCurrentSpeed());

  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    //Code here will run once when robot goes into DISABLED state.
    System.out.println("Robot going into DISABLED state.");
  }

  @Override
  public void disabledPeriodic() {
    //Code here will run constantly while robot is disabled.
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    System.out.println("Teleop mode initiated. Drive Safely.");
    

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //Code here runs constantly while teleop mode is enabled.
    
    //Drive based on joystick inputs
    drivetrain.ArcadeDrive(driveValue, turnValue);


    /*
    //Lifter based on joystick buttons
    if(button5Pressed){
      lifter.AccelerateInNegativeDirection();
    }
    if(button3Pressed){
      lifter.AccelerateInPositiveDirection();
    }
     */


    /*
    //ArmExtender based on joystick buttons
    if(button6Pressed){
      armExtender.AccelerateInPositiveDirection();
    }
    if(button4Pressed && armExtenderLimitHit == false){
      armExtender.AccelerateInNegativeDirection();
    }
 */
    
    //Top Solenoid Pneumatics
    if(gamepadRightBumper && triggerReset){
      //topSolenoid.toggle();
      grabber.TogglePneumatic();
      triggerReset = false;
    } else if(gamepadRightBumper==false){
      triggerReset = true;
    }


   //bottom Pneumatics
   if(button2Pressed && button2Reset){
     //bottomSolenoid.toggle();
     button2Reset = false;
   } else if( button2Pressed==false){
      button2Reset = true;
   }
    
  
   //gamepad controls
   lifter.ControlDirectly(-gamepadLeftStickForward);
   armExtender.ControlDirectly(-gamepadRightStickForward);
   


  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
