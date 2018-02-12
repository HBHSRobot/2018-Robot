/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5966.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5966.robot.commands.DriveBackwards;
import org.usfirst.frc.team5966.robot.commands.DriveForwards;
import org.usfirst.frc.team5966.robot.commands.LiftElevate;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot 
{
	public enum StartingPosition
	{
		LEFT, MIDDLE, RIGHT
	}
	
	public static OI oi;
	CameraServer cameraServer;

	String gameData;
	
	DriveForwards driveForwards;
	DriveBackwards driveBackwards;
	LiftElevate liftElevate;
	SendableChooser<StartingPosition> startingPositionChooser = new SendableChooser<>();

	AnalogInput sensor;
	double volts;
	
	boolean autoDriveFinished;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() 
	{
		oi = new OI();
		//Chooser for the robot's starting position on the dashboard
		startingPositionChooser.addObject("Left", StartingPosition.LEFT);
		startingPositionChooser.addDefault("Middle", StartingPosition.MIDDLE);
		startingPositionChooser.addObject("Right", StartingPosition.RIGHT);
		SmartDashboard.putData("Starting Position", startingPositionChooser);
		autoDriveFinished = false;
		//proximity sensor
		sensor = new AnalogInput(0);
		//Camera Server
		cameraServer = CameraServer.getInstance();
		UsbCamera camera = new UsbCamera("Lifecam", "/dev/video0");
		cameraServer.addCamera(camera);
		cameraServer.startAutomaticCapture(camera);
		System.out.println("Robot Initialization Complete");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() 
	{

	}

	@Override
	public void disabledPeriodic() 
	{
		Scheduler.getInstance().run();
	}
	

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public void autonomousInit() {
		driveForwards = new DriveForwards(true);
		liftElevate = new LiftElevate(true);
		
		// schedule the autonomous command (example)
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		switch(startingPositionChooser.getSelected())
		{
			case LEFT:
				if((gameData.charAt(0) == 'L') && (liftElevate != null)) 
				{
					liftElevate.start();
				}
				break;
			case RIGHT:
				if((gameData.charAt(0) == 'R') && (liftElevate != null)) 
				{
					liftElevate.start();
				}
				break;
		}
		{
			driveForwards.start();
		}
		/* I moved the switch logic to autoPeriodic because the sensor updates through periodic, and only
		 * when the drive is stopped, based on the sensor, is the lift going to operate
		 */
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
		double distance = sensor.getVoltage() / 9.766;
		//2.75 is a placeholder range for the sensor in inches, change to whatever is actually needed
		if(distance >= 2.75) 
		{
			if (driveForwards != null) driveForwards.cancel();
		}
		//just for testing to check that the reading in the program corresponds with the reading fron LabView
		System.out.println(volts);
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (driveForwards != null) 
		{
			driveForwards.changeAutonomousMode();
		}
		if (liftElevate != null)
		{
			liftElevate.changeAutonomousMode();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
